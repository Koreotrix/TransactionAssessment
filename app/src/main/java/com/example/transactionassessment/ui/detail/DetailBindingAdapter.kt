package com.example.transactionassessment.ui.detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.transactionassessment.GST_RATE
import com.example.transactionassessment.ui.shared.formatDate
import com.example.transactionassessment.ui.shared.formatDay
import com.example.transactionassessment.ui.shared.formatMonth
import com.example.transactionassessment.ui.shared.setTransactionText
import com.example.transactionassessment.ui.shared.formatAmount
import java.text.NumberFormat

/**
 * The input is not valid if...
 * ...the date is empty
 * ...the format is incorrect (should be MM-DD-YYYY)
 * ...the date is 0
 * ...the date is more than 31 (regardless of month)
 * ...the month is 0
 * ...the month is more than 12
 *
 * Used in [DetailFragment]
 */
@BindingAdapter("transactionDate")
fun setTransactionDate(view: TextView, date: String): Boolean {

    val day = formatDay(date)
    val month = formatMonth(date)
    val year = formatDate(date).split("-").toTypedArray()[0]

    if (day <= 0 || month.isEmpty() || year.isEmpty()) return false

    val text = "${day} ${month} ${year}"

    return setTransactionText(view, text)

}

/**
 * The input is not valid if...
 * ...the time is empty
 * ...the format is incorrect (Should be HH:MM:SS)
 * ...the hour is less than 0
 * ...the hour is equal or more than 24
 * ...the minute is less than 0
 * ...the minute is equal or more than 60
 * ...the second is less than 0
 * ...the second is equal or more than 60
 *
 * Used in [DetailFragment]
 */
@BindingAdapter("transactionTime")
fun setTransactionTime(view: TextView, date: String): Boolean {

    if (date.isEmpty()) {
        setTransactionText(view, "00:00:00")
        return false
    }

    val text = date.split("T").toTypedArray()[1]
        .split("+").toTypedArray()[0]

    if (text.split(":").toTypedArray()[0].toInt() < 0 // Hour less than 0
            || text.split(":").toTypedArray()[0].toInt() >= 24 // Hour more than 24
            || text.split(":").toTypedArray()[1].toInt() < 0 // Minute less than 0
            || text.split(":").toTypedArray()[1].toInt() >= 60 // Minute more than 60
            || text.split(":").toTypedArray()[2].toInt() < 0 // second less than 0
            || text.split(":").toTypedArray()[2].toInt() >= 60) { // second more than 60

        setTransactionText(view, "00:00:00")
        return false

    }

    return setTransactionText(view, text)

}

/**
 * The input is not valid if...
 * ...the input of both debit and credit is 0.0
 * ...the input of both debit and credit is negative
 */
@BindingAdapter("debitGst", "creditGst")
fun setGstText(view: TextView, debit: Double, credit: Double): Boolean {

    val text = NumberFormat
        .getCurrencyInstance()
        .format(calculateGst(debit, credit))

    return setTransactionText(view, text)
}

fun calculateGst(debit: Double, credit: Double): Double {

    val value = formatAmount(debit, credit)
    return value - (value / GST_RATE)

}