package com.daffaalmerf.footballmatchschedulesqlite.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

object DateTime {

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String, format: String): String {
        var dateResult = ""
        val default = SimpleDateFormat("yyyy-MM-dd")

        try {
            val defaultDate = default.parse(date)
            val formatDate = SimpleDateFormat(format)

            dateResult = formatDate.format(defaultDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dateResult
    }

    fun getLongDate(date: String): String {
        return formatDate(date, "EEE, dd MMM yyyy")
    }
}