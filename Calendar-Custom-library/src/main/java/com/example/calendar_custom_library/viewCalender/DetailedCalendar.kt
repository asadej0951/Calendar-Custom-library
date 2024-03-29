package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.databinding.CalenderDerailedBinding
import com.example.calendar_custom_library.event.EventCalender
import com.example.calendar_custom_library.manager.EventCalenderManager
import com.example.calendar_custom_library.viewCalender.adapter.AdapterDetailedCalendar
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.chrono.ThaiBuddhistChronology
import java.time.format.DateTimeFormatter
import java.util.*

class DetailedCalendar : EventCalenderManager {
    private lateinit var binding: CalenderDerailedBinding

    private lateinit var mEventCalender: EventCalender
    private lateinit var context: Context
    private lateinit var mAdapterDetailedCalendar: AdapterDetailedCalendar

    private val calenderShowView = Calendar.getInstance()
    private val instant = calenderShowView.toInstant()
    private var thaiCalendar =
        ThaiBuddhistChronology.INSTANCE.date(instant.atZone(ZoneId.systemDefault()).toLocalDate())

    private var locale = Locale.US

    private val clickCalendar = Calendar.getInstance()
    private val onClickCalendar = MutableLiveData<Date>()
    private val onClickButtonBackAndNextCalender = MutableLiveData<Date>()
    private var formatter = SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH"))
    private var formatterTh = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("th", "TH", "TH"))
    private var customFont: Typeface? = null


    private val startWeek = ArrayList<String>()
    private var colorTextDay = 0
    private var nameDaySize = 0f
    private lateinit var markDayColor: ColorStateList
    private var dayCalenderColor = 0
    private var markTextDayColor = 0
    private var dayCalenderSize = 0f
    private var startWeekCalender = 1
    private var statusSatSunColorBar = 0
    private var colorSatSunBar: ColorStateList? = null

    private var colorBackgroundToday: Int? = null
    private var colorTextToday: Int? = null

    private val mHashMap = ArrayList<HashMap<String, Any>>()
    private var statusOpenMark = false

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
    ) {

        binding = CalenderDerailedBinding.inflate(LayoutInflater.from(context), viewGroup, true)
        mEventCalender = EventCalender()
        this.context = context
        this.statusOpenMark = statusOpenMark

        // set through view
        startWeek.clear()
        startWeek.addAll(
            mEventCalender.getStartWeek(startWeekCalender, context)
        )
        this.startWeekCalender = startWeekCalender
        setTitleSize(titleSize)
        formatter.format(calenderShowView.time)

        setTitleColor(titleColor)
        setVisibilityLineNameDay(visibilityLineNameDay)
        setLineNameColor(lineColor)
        setLineNameSize(lineSize)
        setStatusSatSunColorBar(statusSatSunColorBar)
        setColorSatSunBar(colorSatSunBar)
        setDrawableButtonBack(drawableButtonBack)
        setDrawableButtonNext(drawableButtonNext)
        setButtonBackSize(buttonBackSize)
        setButtonNextSize(buttonNextSize)

        // set through function
        setNameDayColor(nameDayColor)
        setNameDaySize(nameDaySize)
        setFormatterCalender(simpleDateFormat)

        // set through Adapter
        setDayCalenderSize(dayCalenderSize)
        setMarkDayColor(markDayColor)
        setDayCalenderColor(dayCalenderColor)
        setMarkTextDayColor(markTextDayColor)
        mEventCalender.setGravity(binding.layoutNameDay, gravity)

        when (visibilityButton) {
            1 -> {
                binding.btnBack.visibility = View.INVISIBLE
                binding.btnNext.visibility = View.INVISIBLE
            }
            2 -> {
                binding.btnBack.visibility = View.GONE
                binding.btnNext.visibility = View.GONE
            }
            else -> {
                binding.btnBack.visibility = View.VISIBLE
                binding.btnNext.visibility = View.VISIBLE
            }
        }


        binding.btnBack.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) - 1,
                mEventCalender.getDayInMonth(false, calenderShowView)
            )
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )

            clickCalendar.time = calenderShowView.time
            setRecyclerViewDay()
            onClickButtonBackAndNextCalender.value = calenderShowView.time

        }
        binding.btnNext.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) + 1,
                mEventCalender.getDayInMonth(true, calenderShowView)
            )
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )

            clickCalendar.time = calenderShowView.time
            setRecyclerViewDay()
            onClickButtonBackAndNextCalender.value = calenderShowView.time

        }

        mEventCalender.setDayStartLeft(
            context,
            binding.layoutDay,
            startWeek,
            colorTextDay,
            nameDaySize,
            customFont
        )

        setRecyclerViewDay()


    }

    private fun setRecyclerViewDay() {
        val dates = mEventCalender.setModelDate(calenderShowView, this.startWeekCalender)
        mAdapterDetailedCalendar = AdapterDetailedCalendar(
            context,
            dates,
            this.dayCalenderSize,
            calenderShowView,
            clickCalendar,
            this.dayCalenderColor,
            this.markDayColor,
            this.markTextDayColor,
            this.colorBackgroundToday ?: context.resources.getColor(R.color.bg),
            this.colorTextToday ?: context.resources.getColor(R.color.text_font_black),
            mHashMap, customFont
        ) {
            onClickCalendar.value = it
            clickCalendar.time = it
            calenderShowView.time = clickCalendar.time
            setRecyclerViewDay()
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )

            mAdapterDetailedCalendar.notifyDataSetChanged()
        }
        binding.recyclerViewDay.apply {
            layoutManager =
                GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)
            adapter = mAdapterDetailedCalendar
            mAdapterDetailedCalendar.notifyDataSetChanged()
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
        binding.textDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize)
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
            calenderShowView.time = date
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )

        })
    }

    override fun setFormatterCalender(simpleDateFormat: SimpleDateFormat) {
        formatter = simpleDateFormat
        binding.textDay.text = formatter.format(calenderShowView.time)
    }

    override fun setFormatterCalender(format: String, locale: Locale) {
        this.locale = locale
        formatter = SimpleDateFormat(format, locale)
        formatterTh = DateTimeFormatter.ofPattern(format, Locale("th", "TH", "TH"))
        binding.textDay.text = mEventCalender.checkLocale(
            formatter,
            formatterTh,
            this.locale,
            calenderShowView,
            thaiCalendar
        )
    }

    override fun setCalender(calender: Date) {
        calenderShowView.time = calender
        thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
        binding.textDay.text = mEventCalender.checkLocale(
            formatter,
            formatterTh,
            this.locale,
            calenderShowView,
            thaiCalendar
        )
        setRecyclerViewDay()
    }

    override fun setStartDayOfWeek(startWeek: Int) {
        this.startWeek.clear()
        this.startWeek.addAll(mEventCalender.getStartWeek(startWeek, context))
        mEventCalender.setDayStartLeft(
            context,
            binding.layoutDay,
            this.startWeek,
            colorTextDay,
            nameDaySize,
            customFont
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
        onClickButtonBackAndNextCalender.observe(
            context as LifecycleOwner,
            androidx.lifecycle.Observer {
                callback.invoke(it)
            })
    }

    override fun setStatusSatSunColorBar(statusSatSunColorBar: Int) {
        this.statusSatSunColorBar = statusSatSunColorBar
    }

    override fun setColorSatSunBar(colorSatSunBar: ColorStateList) {
        this.colorSatSunBar = colorSatSunBar
    }

    override fun setDataCalender(mHashMap: java.util.ArrayList<HashMap<String, Any>>) {
        this.mHashMap.clear()
        this.mHashMap.addAll(mHashMap)
        setRecyclerViewDay()
    }

    override fun setDrawableButtonBack(drawableButtonBack: Drawable) {
        binding.btnBack.setImageDrawable(drawableButtonBack)
    }

    override fun setDrawableButtonNext(drawableButtonNext: Drawable) {
        binding.btnNext.setImageDrawable(drawableButtonNext)
    }

    override fun setButtonBackSize(buttonBackSize: Float) {
        val params: ViewGroup.LayoutParams = binding.btnBack.layoutParams
        params.width = buttonBackSize.toInt()
        params.height = buttonBackSize.toInt()
        binding.btnBack.layoutParams = params
    }

    override fun setButtonNextSize(buttonNextSize: Float) {
        val params: ViewGroup.LayoutParams = binding.btnNext.layoutParams
        params.width = buttonNextSize.toInt()
        params.height = buttonNextSize.toInt()
        binding.btnNext.layoutParams = params
    }

    override fun setColorBackgroundToday(colorBackgroundToday: Int) {
        this.colorBackgroundToday = colorBackgroundToday
    }

    override fun setColorTextToday(colorTextToday: Int) {
        this.colorTextToday = colorTextToday
    }

    override fun setFontCalender(fontCalender: String) {
        if (fontCalender != "") {
            customFont = Typeface.createFromAsset(context.assets, fontCalender)
            binding.textDay.typeface = customFont
        }

    }

    override fun setPositionLayoutStatusDay(position: Boolean) {

    }

    override fun setSizeStatusDay(size: Float) {
    }
}