package com.sidekick.apps.habiter

import java.util.*

class Util {

    companion object {
        fun getDaysInMonth (month: Int, year: Int): Int {
            val calendar:Calendar = Calendar.getInstance()
            calendar.set(year,month,1)
            return  calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        fun getColor(i: Int): Int {
            val color = when(i)
            {
                1-> 555
                else-> 111
            }
            return color

        }
    }
}