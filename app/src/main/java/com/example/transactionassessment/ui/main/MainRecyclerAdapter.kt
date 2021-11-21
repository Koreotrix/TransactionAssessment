package com.example.transactionassessment.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.transactionassessment.R
import com.example.transactionassessment.data.Transaction
import com.example.transactionassessment.ui.shared.formatDate
import com.example.transactionassessment.ui.shared.formatDay
import com.example.transactionassessment.ui.shared.formatMonth
import com.example.transactionassessment.ui.shared.setTransactionText
import com.example.transactionassessment.ui.shared.setTransactionType
import com.example.transactionassessment.ui.shared.setTransactionAmount

class MainRecyclerAdapter(
    val context: Context,
    var transactions: List<Transaction>,
    val itemListener: TransactionItemListener
) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    override fun getItemCount() = transactions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.itemlist_transaction, parent, false)

        // sort list by descending order against [transactionDate]
        val arrayList = ArrayList(transactions)
        arrayList.sortByDescending {
            it.transactionDate
        }
        transactions = arrayList

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        sortTransactionList()

        val transaction = transactions[position]

        with(holder) {

            holder.itemView.setOnClickListener {
                itemListener.onTransactionItemClick(transaction)
            }

            monthText?.let {
                setTransactionMonth(it, transaction.transactionDate)
            }
            dayText?.let {
                setTransactionDay(it, transaction.transactionDate)
            }
            summaryText?.let {
                it.text = transaction.summary
                it.contentDescription = transaction.summary
            }
            typeText?.let {
                setTransactionType(it, transaction.debit, transaction.credit)
            }
            amountText?.let {
                setTransactionAmount(it, transaction.debit, transaction.credit)
            }
        }
    }

    interface TransactionItemListener {

        fun onTransactionItemClick(transaction: Transaction)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val monthText = itemView.findViewById<TextView>(R.id.tv_month)
        val dayText = itemView.findViewById<TextView>(R.id.tv_date)
        val summaryText = itemView.findViewById<TextView>(R.id.tv_summary)
        val typeText = itemView.findViewById<TextView>(R.id.tv_type)
        val amountText = itemView.findViewById<TextView>(R.id.tv_amount)

    }

    /**
     * The input is invalid if...
     * ...the date is empty
     * ...the format is incorrect
     * ...the day is 0
     * ...the day is more than 31 (regardless of month)
     *
     * Used in [MainFragment]
     */
    fun setTransactionDay(view: TextView, date: String): Boolean {

        val text = formatDay(date)
        if (text <= 0) return false
        return setTransactionText(view, text.toString())

    }

    /**
     * The input is invalid if...
     * ...the date is empty
     * ...the format is incorrect
     * ...the month is 0
     * ...the month is more than 12
     *
     * Used in [MainFragment]
     */
    fun setTransactionMonth(view: TextView, date: String): Boolean {

        val text = formatMonth(date)
        if (text.isEmpty()) return false
        return setTransactionText(view, text)

    }

    fun sortTransactionList() {
        transactions.sortedBy {
            formatDate(it.transactionDate)
        }
    }
}