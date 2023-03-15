package com.example.calendar_custom_library.event

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.calendar_custom_library.R
import java.util.*

class EventAdapter(private val mContext: Context) {


     fun checkDateToDay(
         daySetModel: Int,
         monthSetModel: Int,
         yearSetModel: Int,
         dateToDay: Calendar,
         clickCalendar: Calendar
     ): Boolean {
        return checkDayDateToday(daySetModel,dateToDay,clickCalendar) && checkMonthDateToday(monthSetModel,dateToDay,clickCalendar) && checkYearDateToday(yearSetModel,dateToDay,clickCalendar)
    }

     private fun checkDayDateToday(daySetModel: Int, dateToDay: Calendar, clickCalendar: Calendar): Boolean {
        return dateToDay[Calendar.DAY_OF_MONTH] == daySetModel && dateToDay[Calendar.DAY_OF_MONTH] != clickCalendar[Calendar.DAY_OF_MONTH]
    }

     private fun checkMonthDateToday(monthSetModel: Int, dateToDay: Calendar, clickCalendar: Calendar): Boolean {
        return dateToDay[Calendar.MONTH] == monthSetModel && dateToDay[Calendar.MONTH] == clickCalendar[Calendar.MONTH]
    }

     private fun checkYearDateToday(yearSetModel: Int, dateToDay: Calendar, clickCalendar: Calendar): Boolean {
        return dateToDay[Calendar.YEAR] == yearSetModel && dateToDay[Calendar.YEAR] == clickCalendar[Calendar.YEAR]
    }

     fun checkToDay(
         daySetModel: Int,
         monthSetModel: Int,
         yearSetModel: Int,
         clickCalendar: Calendar
     ): Boolean {
        return clickCalendar.get(Calendar.DAY_OF_MONTH) == daySetModel && clickCalendar.get(
            Calendar.MONTH
        ) == monthSetModel && clickCalendar.get(Calendar.YEAR) == yearSetModel
    }

     fun checkDayInMonth(monthSetModel: Int, yearSetModel: Int, calenderShowView: Calendar): Boolean {
        return calenderShowView.get(Calendar.MONTH) == monthSetModel && calenderShowView.get(
            Calendar.YEAR
        ) == yearSetModel

    }

     fun checkSatAndSun(dateSetModel: Calendar, position: Int): Drawable? {
        if (position <= 6) {
            return if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 7) {
                mContext.resources.getDrawable(R.drawable.custom_radius_top_bottom_left_13)
            } else if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 1) {
                mContext.resources.getDrawable(R.drawable.custom_radius_top_bottom_rigth_13)
            } else {
                null
            }
        } else if (position >= 35) {
            return if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 7) {
                mContext.resources.getDrawable(R.drawable.custom_radius_bottom_left_13)
            } else if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 1) {
                mContext.resources.getDrawable(R.drawable.custom_radius_bottom_right_13)
            } else {
                null
            }
        } else {
            return if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 7) {
                mContext.resources.getDrawable(R.color.bg)
            } else if (dateSetModel.get(Calendar.DAY_OF_WEEK) == 1) {
                mContext.resources.getDrawable(R.color.bg)
            } else {
                null
            }

        }
    }
}