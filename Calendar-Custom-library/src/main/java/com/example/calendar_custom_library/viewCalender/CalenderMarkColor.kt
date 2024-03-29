package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.Log
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
import com.example.calendar_custom_library.databinding.CalenderCustomBinding
import com.example.calendar_custom_library.event.EventCalender
import com.example.calendar_custom_library.manager.EventCalenderManager
import com.example.calendar_custom_library.viewCalender.adapter.AdapterDayCalenderColorStatus
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.chrono.ThaiBuddhistChronology
import java.time.format.DateTimeFormatter
import java.util.*

class CalenderMarkColor : EventCalenderManager {

    private lateinit var binding: CalenderCustomBinding
    private lateinit var mEventCalender: EventCalender
    private lateinit var context: Context
    private lateinit var mAdapterDayCalenderColorStatus: AdapterDayCalenderColorStatus

    private var locale = Locale.US
    private val calenderShowView = Calendar.getInstance()
    private val instant = calenderShowView.toInstant()
    private var thaiCalendar =
        ThaiBuddhistChronology.INSTANCE.date(instant.atZone(ZoneId.systemDefault()).toLocalDate())


    private var clickCalendar: Calendar? = null
    private val onClickCalendar = MutableLiveData<Date>()
    private val onClickButtonBackAndNextCalender = MutableLiveData<Date>()
    private var formatter = SimpleDateFormat("MMMM yyyy", Locale("th", "TH"))
    private var formatterTh = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("th", "TH", "TH"))

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
    private var customFont: Typeface? = null
    private var positionLayoutStatusDay: Boolean = true
    private var sizeLayoutStatusDay: Float = 10f

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
        drawable: Drawable,
        drawable1: Drawable,
        buttonBackSize: Float,
        buttonNextSize: Float,
        fontCalender: String,
        gravity: Int,
        statusOpenMark: Boolean,
        visibilityButton: Int
    ) {

        binding = CalenderCustomBinding.inflate(LayoutInflater.from(context), viewGroup, true)
        mEventCalender = EventCalender()
        this.context = context
        this.statusOpenMark = statusOpenMark

        // set through view
        startWeek.clear()
        startWeek.addAll(
            mEventCalender.getStartWeek(startWeekCalender, context)
        )
        this.startWeekCalender = startWeekCalender

        clickCalendar = if (statusOpenMark) {
            Calendar.getInstance()
        } else {
            null
        }
        setTitleSize(titleSize)

        setTitleColor(titleColor)
        setVisibilityLineNameDay(visibilityLineNameDay)
        setLineNameColor(lineColor)
        setLineNameSize(lineSize)
        setStatusSatSunColorBar(statusSatSunColorBar)
        setColorSatSunBar(colorSatSunBar)
        setDrawableButtonBack(drawable)
        setDrawableButtonNext(drawable1)
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

        mEventCalender.setGravity(binding.layoutNameDay, gravity)

        binding.btnBack.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) - 1,
                mEventCalender.getDayInMonth(false, calenderShowView)
            )
            clickCalendar?.let {
                it.time = calenderShowView.time
            }

            setRecyclerViewDay()
            Log.i("checkDateTimeBack", formatter.format(
                calenderShowView.time
            ))
            onClickButtonBackAndNextCalender.value = calenderShowView.time
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )

        }
        binding.btnNext.setOnClickListener {
            calenderShowView.set(
                calenderShowView.get(Calendar.YEAR),
                calenderShowView.get(Calendar.MONTH) + 1,
                mEventCalender.getDayInMonth(true, calenderShowView)
            )
            clickCalendar?.let {
                it.time = calenderShowView.time
            }
            setRecyclerViewDay()
            Log.i("checkDateTimeNext", formatter.format(
                calenderShowView.time
            ))
            onClickButtonBackAndNextCalender.value = calenderShowView.time
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )
        }

        mEventCalender.setDayStart(
            context,
            binding.layoutDay,
            startWeek,
            colorTextDay,
            nameDaySize,
            customFont,
            statusSatSunColorBar,
            this.colorSatSunBar ?: context.resources.getColorStateList(R.color.bg)
        )

        setRecyclerViewDay()

    }

    private fun setRecyclerViewDay() {
        val dates = mEventCalender.setModelDate(calenderShowView, this.startWeekCalender)

        Log.i("checkDateHashMap", mHashMap.toString())

        mAdapterDayCalenderColorStatus = AdapterDayCalenderColorStatus(
            context,
            dates,
            this.dayCalenderSize,
            calenderShowView,
            clickCalendar,
            this.dayCalenderColor,
            this.markDayColor,
            this.markTextDayColor,
            this.statusSatSunColorBar,
            this.colorSatSunBar ?: context.resources.getColorStateList(R.color.bg),
            mHashMap,
            customFont,this.positionLayoutStatusDay,sizeLayoutStatusDay
        ) {
            val calenderClick = Calendar.getInstance()
            calenderClick.time = it
            onClickCalendar.value = it
            clickCalendar = calenderClick
            calenderShowView.time = calenderClick.time
            setRecyclerViewDay()
            thaiCalendar = mEventCalender.calenderToThaiCalendar(calenderShowView)
            binding.textDay.text = mEventCalender.checkLocale(
                formatter,
                formatterTh,
                this.locale,
                calenderShowView,
                thaiCalendar
            )
            mAdapterDayCalenderColorStatus.notifyDataSetChanged()
        }
        binding.recyclerViewDay.apply {
            layoutManager =
                GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)
            adapter = mAdapterDayCalenderColorStatus
            mAdapterDayCalenderColorStatus.notifyDataSetChanged()
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
        if (statusOpenMark) {
            clickCalendar = calenderShowView
        }
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
        mEventCalender.setDayStart(
            context,
            binding.layoutDay,
            this.startWeek,
            colorTextDay,
            nameDaySize,
            customFont,
            statusSatSunColorBar,
            colorSatSunBar ?: context.resources.getColorStateList(R.color.bg)
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

                Log.i("checkDateTimeShow", binding.textDay.text.toString())
                Log.i(
                    "checkDateTime", formatter.format(
                        calenderShowView.time
                    )
                )
                callback.invoke(it)
            })
    }

    override fun setStatusSatSunColorBar(statusSatSunColorBar: Int) {
        this.statusSatSunColorBar = statusSatSunColorBar
    }

    override fun setColorSatSunBar(colorSatSunBar: ColorStateList) {
        this.colorSatSunBar = colorSatSunBar
    }

    override fun setDataCalender(mHashMap: ArrayList<HashMap<String, Any>>) {
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

    }

    override fun setColorTextToday(colorTextToday: Int) {

    }

    override fun setFontCalender(fontCalender: String) {
        if (fontCalender != "") {
            customFont = Typeface.createFromAsset(context.assets, fontCalender)
            binding.textDay.typeface = customFont
        }

    }

    override fun setPositionLayoutStatusDay(position: Boolean) {
       this.positionLayoutStatusDay = position
        setRecyclerViewDay()
    }

    override fun setSizeStatusDay(size: Float) {
       this.sizeLayoutStatusDay = size
        setRecyclerViewDay()
    }
}