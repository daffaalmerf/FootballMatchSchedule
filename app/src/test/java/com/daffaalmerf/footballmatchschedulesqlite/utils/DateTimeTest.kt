package com.daffaalmerf.footballmatchschedulesqlite.utils

import com.daffaalmerf.footballmatchschedulesqlite.utils.DateTime.getLongDate
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

class DateTimeTest {

    @Test
    fun testGetLongDate() {
        var dateResult = ""
        val default = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = default.parse("2018-02-28")
        val formatDate = SimpleDateFormat("yyyy-MM-dd")
        dateResult = formatDate.format(dateFormat)
        assertEquals("Wed, 28 Feb 2018", getLongDate(dateResult))
    }
}