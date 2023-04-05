package com.example.calendar_custom_library.manager

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
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
        statusSatSunColorBar: Int,
        colorSatSunBar: ColorStateList,
        drawableButtonBack: Drawable,
        drawableButtonNext: Drawable,
        buttonBackSize: Float,
        buttonNextSize: Float,
        fontCalender: String,
        gravity: Int,
        statusOpenMark: Boolean,
        visibilityButton: Int
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

    fun setFormatterCalender(format: String, locale: Locale)
    fun setCalender(calender: Date)
    fun setStartDayOfWeek(startWeek: Int)

    fun setLineNameColor(color: Int)
    fun setLineNameSize(size: Int)

    fun setOnClickButtonBackAndNextCalender(callback: ((Date) -> Unit))

    fun setStatusSatSunColorBar(statusSatSunColorBar: Int)
    fun setColorSatSunBar(colorSatSunBar: ColorStateList)

    fun setDataCalender(mHashMap: ArrayList<HashMap<String, Any>>)

    fun setDrawableButtonBack(drawableButtonBack: Drawable)

    fun setDrawableButtonNext(drawableButtonNext: Drawable)

    fun setButtonBackSize(buttonBackSize: Float)
    fun setButtonNextSize(buttonNextSize: Float)

    fun setColorBackgroundToday(colorBackgroundToday :Int)
    fun setColorTextToday(colorTextToday :Int)

    fun setFontCalender(fontCalender: String)




}