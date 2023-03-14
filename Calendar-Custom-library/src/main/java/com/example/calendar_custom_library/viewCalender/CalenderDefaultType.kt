package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendar_custom_library.databinding.CalenderCustomBinding
import com.example.calendar_custom_library.event.EventCalender
import com.example.calendar_custom_library.manager.EventCalenderManager
import com.example.calendar_custom_library.viewCalender.adapter.AdapterDayCalendar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalenderDefaultType : EventCalenderManager {
    private lateinit var binding: CalenderCustomBinding
    private lateinit var mEventCalender: EventCalender
    private lateinit var context: Context
    private lateinit var mAdapterDayCalendar: AdapterDayCalendar

    private val calenderShowView = Calendar.getInstance()
    private val clickCalendar = Calendar.getInstance()
    private val onClickCalendar = MutableLiveData<Date>()
    private val onClickButtonBackAndNextCalender =MutableLiveData<Date>()
    private var formatter = SimpleDateFormat("MMMM yyyy", Locale("th", "TH"))


    private val startWeek = ArrayList<String>()
    private var colorTextDay = 0
    private var nameDaySize = 0f
    private lateinit var markDayColor: ColorStateList
    private var dayCalenderColor = 0
    private var markTextDayColor = 0
    private var dayCalenderSize = 0f
    private var startWeekCalender = 1


    override fun initViewCalender(
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
    ) {
        binding = CalenderCustomBinding.inflate(LayoutInflater.from(context), viewGroup, true)
        mEventCalender = EventCalender()
        this.context = context

        // set through view
        binding.textDay.text = formatter.format(calenderShowView.time)
        startWeek.clear()
        startWeek.addAll(
            mEventCalender.getStartWeek(startWeekCalender, context)
        )
        this.startWeekCalender = startWeekCalender
        setTitleSize(titleSize)

        setTitleColor(titleColor)
        setVisibilityLineNameDay(visibilityLineNameDay)
        setLineNameColor(lineColor)
        setLineNameSize(lineSize)

        // set through function
        setNameDayColor(nameDayColor)
        setNameDaySize(nameDaySize)
        setFormatterCalender(simpleDateFormat)
        setCalender(calender)

        // set through Adapter
        setDayCalenderSize(dayCalenderSize)
        setMarkDayColor(markDayColor)
        setDayCalenderColor(dayCalenderColor)
        setMarkTextDayColor(markTextDayColor)


        binding.btnBack.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) - 1,
                mEventCalender.getDayInMonth(false, calenderShowView)
            )
            binding.textDay.text = formatter.format(calenderShowView.time)
            setRecyclerViewDay()

            onClickButtonBackAndNextCalender.value = calenderShowView.time

        }
        binding.btnNext.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) + 1,
                mEventCalender.getDayInMonth(true, calenderShowView)
            )
            binding.textDay.text = formatter.format(calenderShowView.time)
            setRecyclerViewDay()
            onClickButtonBackAndNextCalender.value = calenderShowView.time
        }

        mEventCalender.setDayStart(
            context,
            binding.layoutDay,
            startWeek,
            colorTextDay,
            nameDaySize
        )

        setRecyclerViewDay()

    }

    private fun setRecyclerViewDay() {
        val dates = mEventCalender.setModelDate(calenderShowView, this.startWeekCalender)
        mAdapterDayCalendar = AdapterDayCalendar(
            context,
            dates,
            this.dayCalenderSize,
            calenderShowView,
            clickCalendar,
            this.dayCalenderColor,
            this.markDayColor,
            this.markTextDayColor
        ) {
            onClickCalendar.value = it
            clickCalendar.time = it
            mAdapterDayCalendar.notifyDataSetChanged()
        }
        binding.recyclerViewDay.apply {
            layoutManager =
                GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)
            adapter = mAdapterDayCalendar
            mAdapterDayCalendar.notifyDataSetChanged()
        }
    }

    override fun setTitleColor(titleColor: Int) {
        binding.textDay.setTextColor(titleColor)
    }

    override fun setNameDayColor(nameDayColor: Int) {
        colorTextDay = nameDayColor
    }

    override fun setMarkDayColor(markDayColor: ColorStateList) {
        this.markDayColor = markDayColor
    }

    override fun setDayCalenderColor(dayCalenderColor: Int) {
        this.dayCalenderColor = dayCalenderColor
    }

    override fun setMarkTextDayColor(markTextDayColor: Int) {
        this.markTextDayColor = markTextDayColor
    }

    override fun setVisibilityLineNameDay(visibilityLineNameDay: Int) {
        binding.lineNameDayToNameWeek.visibility = when (visibilityLineNameDay) {
            1 -> View.INVISIBLE
            2 -> View.GONE
            else -> View.VISIBLE
        }
    }

    override fun setTitleSize(titleSize: Float) {
        binding.textDay.textSize = titleSize
    }

    override fun setNameDaySize(nameDaySize: Float) {
        this.nameDaySize = nameDaySize
    }

    override fun setDayCalenderSize(dayCalenderSize: Float) {
        this.dayCalenderSize = dayCalenderSize
    }

    override fun setOnClickListener(callback: (Date) -> Unit) {
        onClickCalendar.observe(context as LifecycleOwner, androidx.lifecycle.Observer { date ->
            callback.invoke(date)
        })
    }

    override fun setFormatterCalender(simpleDateFormat: SimpleDateFormat) {
        formatter = simpleDateFormat
    }

    override fun setCalender(calender: Date) {
        calenderShowView.time = calender
    }

    override fun setStartDayOfWeek(startWeek: Int) {
        this.startWeek.clear()
        this.startWeek.addAll(mEventCalender.getStartWeek(startWeek, context))
        mEventCalender.setDayStart(
            context,
            binding.layoutDay,
            this.startWeek,
            colorTextDay,
            nameDaySize
        )
    }

    override fun setLineNameColor(color: Int) {
        binding.lineNameDayToNameWeek.setBackgroundColor(color)
    }

    override fun setLineNameSize(size: Int) {
        val params = LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, size)
        binding.lineNameDayToNameWeek.layoutParams = params
    }

    override fun setOnClickButtonBackAndNextCalender(callback: (Date) -> Unit) {
        onClickButtonBackAndNextCalender.observe(context as LifecycleOwner,  androidx.lifecycle.Observer {
            callback.invoke(it)
        })
    }
}