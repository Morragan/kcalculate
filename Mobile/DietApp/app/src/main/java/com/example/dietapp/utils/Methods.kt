package com.example.dietapp.utils

import java.util.*

object Methods {
    fun datesAreTheSameDay(date1: Date, date2: Date): Boolean{
        val cal1: Calendar = Calendar.getInstance()
        val cal2: Calendar = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }

    fun offsetDate(date: Date, daysOffset: Int): Date{
        val calendar = Calendar.getInstance()
        calendar.time  =date
        calendar.add(Calendar.DAY_OF_YEAR, daysOffset)
        return calendar.time
    }
}
