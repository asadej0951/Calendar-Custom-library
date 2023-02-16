package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import java.text.SimpleDateFormat
import java.util.*


class CalenderCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val MON = 1
        const val TUE = 2
        const val WED = 3
        const val THU = 4
        const val FRI = 5
        const val SAT = 6
        const val SUN = 7
        const val MAX_CALENDAR_DAYS = 42

        val calender = Calendar.getInstance()
        var startWeek = 2
    }

    private var mContext: Context? = null

    private lateinit var mAdapterDayCalendar :AdapterDayCalendar

    private val nameDay = arrayListOf<String>("จ", "อ", "พ", "พฤ", "ศ", "ส", "อา")

    private var formatter = SimpleDateFormat("MMMM yyyy", Locale("th", "TH"))

    private var layoutDay: LinearLayout? = null
    private var textDay: TextView? = null
    private var lineNameDayToNameWeek: TextView? = null
    private var recyclerViewDay: RecyclerView? = null

    private var dates: MutableList<Date> = ArrayList()
    private var week = 0

    init {
        mContext = context
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.calender_custom, this)
        setStartDayOfWeek(MON)
        layoutDay = findViewById(R.id.layoutDay)
        textDay = findViewById(R.id.textDay)
        lineNameDayToNameWeek = findViewById(R.id.lineNameDayToNameWeek)
        recyclerViewDay = findViewById(R.id.recyclerViewDay)
        setDayStart(com.google.android.material.R.color.design_default_color_on_background)
        textDay?.let { textDay ->
            textDay.text = formatter.format(calender.time)
        }
        setModelDate()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerViewDay?.let {recyclerViewDay ->
            mContext?.let {mContext->
                mAdapterDayCalendar = AdapterDayCalendar(mContext,dates)
                recyclerViewDay.apply {
                    layoutManager =  GridLayoutManager(mContext, 7, GridLayoutManager.VERTICAL, false)
                    adapter = mAdapterDayCalendar
                    mAdapterDayCalendar.notifyDataSetChanged()
                }
            }

        }
    }

    private fun setDayStart(colorTextDay: Int) {
        layoutDay?.let { layoutDay ->
            layoutDay.removeAllViews()
            mContext?.let {
                for (i in 1..nameDay.size) {
                    val tvNameDay = AppCompatTextView(it)
                    val params = LinearLayout.LayoutParams(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    tvNameDay.gravity = Gravity.CENTER
                    tvNameDay.layoutParams = params
                    tvNameDay.text = nameDay[i - 1]
                    tvNameDay.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    tvNameDay.setTextColor(it.resources.getColor(colorTextDay))
                    tvNameDay.textSize = 14f
                    layoutDay.addView(tvNameDay)
                }
            }
        }
    }

    fun setTextColorDay(colorTextDay: Int) {
        setDayStart(colorTextDay)
    }

    fun setStartDayOfWeek(MON: Int) {
        nameDay.clear()
        when (MON) {
            2 -> {
                nameDay.addAll(arrayListOf<String>("อ", "พ", "พฤ", "ศ", "ส", "อา", "จ"))
                startWeek = 3
            }
            3 -> {
                nameDay.addAll(arrayListOf<String>("พ", "พฤ", "ศ", "ส", "อา", "จ", "อ"))
                startWeek = 4
            }
            4 -> {
                nameDay.addAll(arrayListOf<String>("พฤ", "ศ", "ส", "อา", "จ", "อ", "พ"))
                startWeek = 5
            }
            5 -> {
                nameDay.addAll(arrayListOf<String>("ศ", "ส", "อา", "จ", "อ", "พ", "พฤ"))
                startWeek = 6
            }
            6 -> {
                nameDay.addAll(arrayListOf<String>("ส", "อา", "จ", "อ", "พ", "พฤ", "ศ"))
                startWeek = 0
            }
            7 -> {
                nameDay.addAll(arrayListOf<String>("อา", "จ", "อ", "พ", "พฤ", "ศ", "ส"))
                startWeek = 1
            }
            else -> {
                nameDay.addAll(arrayListOf<String>("จ", "อ", "พ", "พฤ", "ศ", "ส", "อา"))
                startWeek = 2
            }
        }
    }

    fun setFormatterCalender(simpleDateFormat: SimpleDateFormat) {
        formatter = simpleDateFormat
        textDay?.let { textDay ->
            textDay.text = formatter.format(calender.time)
        }
    }

    fun setModelDate() {
        val monthCalendar = calender.clone() as Calendar
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
    }

    fun setColorLine() {
        lineNameDayToNameWeek?.let { lineNameDayToNameWeek ->
            lineNameDayToNameWeek.setBackgroundColor(resources.getColor(com.google.android.material.R.color.primary_material_dark))

        }
    }

}