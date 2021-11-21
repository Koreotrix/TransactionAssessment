package com.example.transactionassessment.ui.shared

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat

/**
 * The input is not valid if...
 * ...the input of both debit and credit is 0.0
 * ...the input of both debit and credit is negative
 *
 * Used in [MainFragment] and [DetailFragment]
 */
@BindingAdapter("debitType", "creditType")
fun setTransactionType(view: TextView, debit: Double, credit: Double): Boolean {

    if (debit <= 0.0 && credit <= 0.0) return false

    return when (debit) {
        0.0 -> setTransactionText(view, "Credit")
        else -> setTransactionText(view, "Debit")
    }

}

/**
 * The input is not valid if...
 * ...the input of both debit and credit is 0.0
 * ...the input of both debit and credit is negative
 *
 * Used in [MainFragment] and [DetailFragment]
 */
@BindingAdapter("debitAmount", "creditAmount")
fun setTransactionAmount(view: TextView, debit: Double, credit: Double): Boolean {

    if (debit <= 0.0 && credit <= 0.0) return false

    val text = NumberFormat
        .getCurrencyInstance()
        .format(formatAmount(debit, credit))

    val color = when (debit) {
        0.0 -> Color.GREEN
        else -> Color.RED
    }

    view.setTextColor(color)
    return setTransactionText(view, text)

}

fun setTransactionText(view: TextView, text: String): Boolean {

    if (text.isEmpty()) return false

    view.text = text
    view.contentDescription = text

    return true
}

fun formatDate(date: String): String {

    if (date.isEmpty()) return ""
    return date.split("T")
        .toTypedArray()[0]
}

fun formatDay(date: String): Int {

    if (date.isEmpty()) return -1

    val day = formatDate(date)
        .split("-") // yyyy-mm-dd format
        .toTypedArray()[2]
        .toInt()

    if (day <= 0 || day > 31) return -1

    return day
}

fun formatMonth(date: String): String {

    if (date.isEmpty()) return ""

    return when(date.split("T")
            .toTypedArray()[0] // full date
            .split("-") // yyyy-mm-dd format
            .toTypedArray()[1]
            .toInt()) {
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "Apr"
        5 -> "May"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Aug"
        9 -> "Sep"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> ""
    }

}

fun formatAmount(debit: Double, credit: Double): Double {

    if (debit <= 0.0 && credit <= 0.0) return -0.1

    return when (debit) {
        0.0 -> credit
        else -> debit
    }

}
