package com.example.calendar_custom_library.viewCalender

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.viewCalender.ObjectCalender.MAX_CALENDAR_DAYS
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UseCompatLoadingForColorStateLists,PrivateResource,NotifyDataSetChanged")
class CalenderCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val calender = Calendar.getInstance()
    private var startWeek = 2
    private var sizeText = 18f
    private val onClickCalendar = MutableLiveData<Date>()

    private var colorTextDay: Int = R.color.text_font_black
    private var colorMarkDay: Int = R.color.orange_new
    private var colorTextMarkDay: Int = R.color.white
    private var colorTextHeaderCalender = R.color.text_font_black

    private val clickCalendar = Calendar.getInstance()
    private var mContext: Context? = null

    private lateinit var mAdapterDayCalendar: AdapterDayCalendar

    private val nameDay = arrayListOf("จ", "อ", "พ", "พฤ", "ศ", "ส", "อา")

    private var formatter = SimpleDateFormat("MMMM yyyy", Locale("th", "TH"))

    private var layoutDay: LinearLayout? = null
    private var textDay: TextView? = null
    private var lineNameDayToNameWeek: TextView? = null
    private var recyclerViewDay: RecyclerView? = null
    private var btnBack: ImageButton? = null
    private var btnNext: ImageButton? = null

    private var dates: MutableList<Date> = ArrayList()
    private var week = 0

    init {
        mContext = context
        init(attrs)
    }


    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.calender_custom, this)

        layoutDay = findViewById(R.id.layoutDay)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        textDay = findViewById(R.id.textDay)
        lineNameDayToNameWeek = findViewById(R.id.lineNameDayToNameWeek)
        recyclerViewDay = findViewById(R.id.recyclerViewDay)
        setDayStart(com.google.android.material.R.color.design_default_color_on_background)
        textDay?.let { textDay ->
            textDay.textSize = sizeText
            textDay.text = formatter.format(calender.time)
        }
        setModelDate()
        setRecyclerView()
        eventClickButtonNextBack()
    }

    private fun resetViewAll() {
        setDayStart(com.google.android.material.R.color.design_default_color_on_background)
        textDay?.let { textDay ->
            textDay.textSize = sizeText
            textDay.text = formatter.format(calender.time)
        }
        setModelDate()
        setRecyclerView()
        eventClickButtonNextBack()
    }

    private fun eventClickButtonNextBack() {
        btnNext?.let {
            it.setOnClickListener {
                calender.set(
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH) + 1,
                    getDayInMonth(true)
                )
                resetRecyclerView()
            }
        }
        btnBack?.let {
            it.setOnClickListener {
                calender.set(
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH) - 1,
                    getDayInMonth(false)
                )
                resetRecyclerView()
            }
        }
    }

    fun setCalender(calender: Date) {
        this.calender.time = calender
        resetViewAll()
    }

    fun setSizeText(sizeText: Float) {
        this.sizeText = sizeText
        resetViewAll()
    }

    private fun resetRecyclerView() {
        textDay?.let { textDay ->
            textDay.text = formatter.format(calender.time)
        }
        setModelDate()
        mAdapterDayCalendar.notifyDataSetChanged()
    }

    fun setOnClickCalender(callBack: ((Date) -> Unit)) {
        mContext?.let {
            onClickCalendar.observe(it as LifecycleOwner, androidx.lifecycle.Observer { date ->
                callBack.invoke(date)
            })
        }

    }

    fun setColorTextHeaderCalender(colorTextHeaderCalender: Int) {
        this.colorTextHeaderCalender = colorTextHeaderCalender
        textDay?.setTextColor(resources.getColor(colorTextHeaderCalender))

    }


    fun setTintButtonNextAndBack(colorTint: Int) {
        btnBack?.let {
            it.imageTintList = resources.getColorStateList(colorTint)
        }
        btnNext?.let {
            it.imageTintList = resources.getColorStateList(colorTint)
        }
    }

    fun setColorTextDay(colorTextDay: Int) {
        this.colorTextDay = colorTextDay
        setRecyclerView()
    }

    fun setColorMarkDay(colorMarkDay: Int) {
        this.colorMarkDay = colorMarkDay
        setRecyclerView()
    }

    fun setColorTextMarkDay(colorMarkDay: Int) {
        this.colorMarkDay = colorMarkDay
        setRecyclerView()
    }

    fun setColorTextDayAndColorMarkDay(colorTextDay: Int, colorMarkDay: Int) {
        this.colorTextDay = colorMarkDay
        this.colorMarkDay = colorMarkDay
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerViewDay?.let { recyclerViewDay ->
            mContext?.let { mContext ->
                mAdapterDayCalendar = AdapterDayCalendar(
                    mContext,
                    dates,
                    sizeText,
                    calender,
                    clickCalendar,
                    colorTextDay,
                    colorMarkDay,
                    colorTextMarkDay
                ) {
                    onClickCalendar.value = it
                    clickCalendar.time = it
                    mAdapterDayCalendar.notifyDataSetChanged()
                }
                recyclerViewDay.apply {
                    layoutManager =
                        GridLayoutManager(mContext, 7, GridLayoutManager.VERTICAL, false)
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
                    tvNameDay.setTextColor(colorTextDay)
                    tvNameDay.textSize = sizeText
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
                nameDay.addAll(arrayListOf("อ", "พ", "พฤ", "ศ", "ส", "อา", "จ"))
                startWeek = 3
            }
            3 -> {
                nameDay.addAll(arrayListOf("พ", "พฤ", "ศ", "ส", "อา", "จ", "อ"))
                startWeek = 4
            }
            4 -> {
                nameDay.addAll(arrayListOf("พฤ", "ศ", "ส", "อา", "จ", "อ", "พ"))
                startWeek = 5
            }
            5 -> {
                nameDay.addAll(arrayListOf("ศ", "ส", "อา", "จ", "อ", "พ", "พฤ"))
                startWeek = 6
            }
            6 -> {
                nameDay.addAll(arrayListOf("ส", "อา", "จ", "อ", "พ", "พฤ", "ศ"))
                startWeek = 0
            }
            7 -> {
                nameDay.addAll(arrayListOf("อา", "จ", "อ", "พ", "พฤ", "ศ", "ส"))
                startWeek = 1
            }
            else -> {
                nameDay.addAll(arrayListOf("จ", "อ", "พ", "พฤ", "ศ", "ส", "อา"))
                startWeek = 2
            }
        }

        resetViewAll()
    }

    fun setFormatterCalender(simpleDateFormat: SimpleDateFormat) {
        formatter = simpleDateFormat
        textDay?.let { textDay ->
            textDay.text = formatter.format(calender.time)
        }
    }

    private fun setModelDate() {
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

    fun setColorLine(color: Int) {
        lineNameDayToNameWeek?.let { lineNameDayToNameWeek ->
            lineNameDayToNameWeek.setBackgroundColor(color)

        }
    }

    fun setLineNameDayToNameWeekVisibility(visibility: Int) {
        lineNameDayToNameWeek?.let { lineNameDayToNameWeek ->
            lineNameDayToNameWeek.visibility = visibility

        }
    }

    private fun getDayInMonth(statusClick: Boolean): Int {
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