package com.example.calendar_custom_library.manager

import android.content.Context
import android.content.res.ColorStateList
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

interface EventCalenderManager {
    fun initViewCalender(
        context: Context,
        viewGroup: ViewGroup,
        titleColor: Int,
        nameDayColor: Int,
        markDayColor: ColorStateList,
        markTextDayColor: Int,
        dayCalenderColor: Int,
        titleSize: Float,
        nameDaySize: Float,
        dayCalenderSize: Float,
        visibilityLineNameDay: Int,
        startWeekCalender: Int,
        simpleDateFormat: SimpleDateFormat,
        calender: Date,
        lineColor: Int,
        lineSize: Int,
    )

    fun setTitleColor(titleColor: Int)
    fun setNameDayColor(nameDayColor: Int)
    fun setMarkDayColor(markDayColor: ColorStateList)
    fun setDayCalenderColor(dayCalenderColor: Int)
    fun setMarkTextDayColor(markTextDayColor: Int)
    fun setVisibilityLineNameDay(visibilityLineNameDay: Int)

    fun setTitleSize(titleSize: Float)
    fun setNameDaySize(nameDaySize: Float)
    fun setDayCalenderSize(dayCalenderSize: Float)

    fun setOnClickListener(callback: ((Date) -> Unit))
    fun setFormatterCalender(simpleDateFormat: SimpleDateFormat)
    fun setCalender(calender: Date)
    fun setStartDayOfWeek(startWeek: Int)

    fun setLineNameColor(color : Int)
    fun setLineNameSize(size : Int)

    fun setOnClickButtonBackAndNextCalender(callback: ((Date) -> Unit))
}