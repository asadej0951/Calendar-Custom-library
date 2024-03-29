package com.example.calendar_custom_library.event

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.viewCalender.adapter.AdapterDayCalendar
import com.example.calendar_custom_library.viewCalender.objectCalender.ObjectCalender.MAX_CALENDAR_DAYS
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.chrono.ThaiBuddhistChronology
import java.time.chrono.ThaiBuddhistDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class EventCalender {

    private lateinit var mAdapterDayCalendar: AdapterDayCalendar

    fun setDayStart(
        mContext: Context,
        layoutDay: LinearLayout,
        nameDay: ArrayList<String>,
        colorTextDay: Int,
        sizeText: Float,
        customFont: Typeface?,
        statusSatSunColorBar: Int,
        colorStateList: ColorStateList
    ) {
        layoutDay.removeAllViews()
        for (i in 1..nameDay.size) {
            val tvNameDay = AppCompatTextView(mContext)
            val params = LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            tvNameDay.gravity = Gravity.CENTER
            tvNameDay.layoutParams = params
            tvNameDay.text = nameDay[i - 1]
            tvNameDay.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            tvNameDay.setTextColor(colorTextDay)
            tvNameDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeText)
            if (statusSatSunColorBar == 1) {
                tvNameDay.background =
                    if (nameDay[i - 1] == "ส" || nameDay[i - 1] == "Sat")
                        mContext.resources.getDrawable(
                            R.drawable.custom_radius_top_bottom_left_13
                        ) else if (nameDay[i - 1] == "อา" || nameDay[i - 1] == "Sun") mContext.resources.getDrawable(
                        R.drawable.custom_radius_top_bottom_rigth_13
                    ) else null

                tvNameDay.backgroundTintList = colorStateList
            }

            layoutDay.addView(tvNameDay)
        }
    }

    fun calenderToThaiCalendar(calenderShowView: Calendar): ThaiBuddhistDate {
        return ThaiBuddhistChronology.INSTANCE.date(
            calenderShowView.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        )
    }

    fun checkLocale(
        formatter: SimpleDateFormat,
        formatterTh: DateTimeFormatter,
        locale: Locale,
        calenderShowView: Calendar,
        thaiCalendar: ThaiBuddhistDate
    ): String {
        return if (locale.language == "th") formatterTh.format(thaiCalendar) else formatter.format(
            calenderShowView.time
        )
    }

    fun setGravity(layoutNameDay: LinearLayoutCompat, gravity: Int) {
        layoutNameDay.gravity = Gravity.CENTER_VERTICAL
        layoutNameDay.gravity = when (gravity) {
            1 -> Gravity.START
            2 -> Gravity.END
            else -> Gravity.CENTER
        }
    }

    fun setDayStartLeft(
        mContext: Context,
        layoutDay: LinearLayout,
        nameDay: ArrayList<String>,
        colorTextDay: Int,
        sizeText: Float,
        customFont: Typeface?
    ) {
        layoutDay.removeAllViews()
        for (i in 1..nameDay.size) {
            val tvNameDay = AppCompatTextView(mContext)
            val params = LinearLayout.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            )
            tvNameDay.gravity = Gravity.CENTER
            tvNameDay.layoutParams = params
            tvNameDay.text = nameDay[i - 1]
            tvNameDay.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
            tvNameDay.setTextColor(colorTextDay)
            tvNameDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeText)
            layoutDay.addView(tvNameDay)

        }
    }

    fun getStartWeek(id: Int, context: Context): Array<out String> = when (id) {
        1 -> context.resources.getStringArray(R.array.day_week_mon)
        2 -> context.resources.getStringArray(R.array.day_week_tue)
        3 -> context.resources.getStringArray(R.array.day_week_wed)
        4 -> context.resources.getStringArray(R.array.day_week_thu)
        5 -> context.resources.getStringArray(R.array.day_week_fri)
        6 -> context.resources.getStringArray(R.array.day_week_sat)
        else -> context.resources.getStringArray(R.array.day_week_sun)
    }

    fun setModelDate(calender: Calendar, startWeekCalender: Int): MutableList<Date> {
        val dates: MutableList<Date> = ArrayList()
        var week = 0
        val monthCalendar = calender.clone() as Calendar
        val startWeek = getIntStartWeek(startWeekCalender)
        monthCalendar[Calendar.DAY_OF_MONTH] = -1
        val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] - startWeek
        monthCalendar.add(
            Calendar.DAY_OF_MONTH,
            if (monthCalendar[Calendar.DAY_OF_WEEK] == 7) startWeek else firstDayOfMonth * -1
        )
        if (monthCalendar[Calendar.DAY_OF_MONTH] in 2..6) {
            monthCalendar.add(Calendar.DAY_OF_MONTH, -7)
        }
        dates.clear()
        while (dates.size < MAX_CALENDAR_DAYS) {
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        dates.mapIndexed { index, date ->
            val dateCalendar = Calendar.getInstance()
            dateCalendar.time = date
            if (dateCalendar[Calendar.DAY_OF_MONTH] == calender[Calendar.DAY_OF_MONTH]) {
                week = index
            }
        }
        return dates
    }

    private fun getIntStartWeek(startWeekCalender: Int): Int =
        when (startWeekCalender) {
            2 -> 3
            3 -> 4
            4 -> 5
            5 -> 6
            6 -> 0
            7 -> 1
            else -> 2
        }

    fun getDayInMonth(
        statusClick: Boolean,
        calender: Calendar
    ): Int {
        var day = 0
        val cal = Calendar.getInstance()
        val month =
            if (statusClick) calender.get(Calendar.MONTH) + 1 else calender.get(Calendar.MONTH) - 1
        cal.set(calender.get(Calendar.YEAR), month, 1)
        cal.set(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH) + 1, 1)
        day =
            if (calender.get(Calendar.DAY_OF_MONTH) > cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            } else {
                calender.get(Calendar.DAY_OF_MONTH)
            }
        return day
    }

}