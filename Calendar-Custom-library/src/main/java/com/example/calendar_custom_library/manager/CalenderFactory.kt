package com.example.calendar_custom_library.manager

import com.example.calendar_custom_library.theme.TypeCalenderCustom
import com.example.calendar_custom_library.viewCalender.CalenderDefaultType

object CalenderFactory {
    fun build(mTypeCalenderCustom: TypeCalenderCustom): EventCalenderManager =
        when (mTypeCalenderCustom) {
            TypeCalenderCustom.DefaultCalender -> CalenderDefaultType()
        }
}