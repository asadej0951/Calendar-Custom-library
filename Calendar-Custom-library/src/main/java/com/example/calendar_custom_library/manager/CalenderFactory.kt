package com.example.calendar_custom_library.manager

import com.example.calendar_custom_library.theme.TypeCalenderCustom
import com.example.calendar_custom_library.viewCalender.CalenderDefaultType
import com.example.calendar_custom_library.viewCalender.CalenderMarkColor
import com.example.calendar_custom_library.viewCalender.DetailedCalendar

object CalenderFactory {
    fun build(mTypeCalenderCustom: TypeCalenderCustom): EventCalenderManager =
        when (mTypeCalenderCustom) {
            TypeCalenderCustom.CalenderMarkColor -> CalenderMarkColor()
            TypeCalenderCustom.CalenderDetailed -> DetailedCalendar()
            else -> CalenderDefaultType()
        }
}