package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.manager.CalenderFactory
import com.example.calendar_custom_library.manager.EventCalenderManager
import com.example.calendar_custom_library.theme.TypeCalenderCustom
import java.text.SimpleDateFormat
import java.util.*


class CalenderCustom : ConstraintLayout {
    private lateinit var manager: EventCalenderManager
    private var formatter = SimpleDateFormat("MMMM yyyy", Locale("th", "TH"))

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        readAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        readAttrs(attrs)
    }

    private fun readAttrs(attrs: AttributeSet) {
        with(context.theme.obtainStyledAttributes(attrs, R.styleable.CalenderCustom, 0, 0)) {
            inflateView(
                getSwitchTypeFromAttr(this),
                getInt(
                    R.styleable.CalenderCustom_color_text_title,
                    resources.getColor(R.color.text_font_black)
                ),
                getInt(
                    R.styleable.CalenderCustom_color_text_name_day,
                    resources.getColor(R.color.text_font_black)
                ),
                getColorStateList(R.styleable.CalenderCustom_color_mark_day),
                getInt(
                    R.styleable.CalenderCustom_color_text_day_calender,
                    resources.getColor(R.color.text_font_black)
                ),
                getInt(
                    R.styleable.CalenderCustom_color_text_mark_day_calender,
                    resources.getColor(R.color.white)
                ),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_text_title,
                    spToPx(8f, context)
                ).toFloat(),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_text_name_day,
                    spToPx(8f, context)
                ).toFloat(),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_text_day_calender,
                    spToPx(8f, context)
                ).toFloat(),
                getInt(R.styleable.CalenderCustom_visibility_line_name_day, 0),
                getInt(R.styleable.CalenderCustom_start_week_calender, 1),
                getInt(
                    R.styleable.CalenderCustom_color_line_name_day,
                    resources.getColor(R.color.text_font_black)
                ),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_line_name_day,
                    spToPx(1f, context)
                ),
                getBoolean(R.styleable.CalenderCustom_status_sat_sun_color_bar, true),
                getColorStateList(R.styleable.CalenderCustom_color_sat_sun_bar),
                getDrawable(R.styleable.CalenderCustom_src_button_back),
                getDrawable(R.styleable.CalenderCustom_src_button_next),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_button_back,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).toFloat(),
                getDimensionPixelSize(
                    R.styleable.CalenderCustom_size_button_back,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).toFloat(),
                getString(R.styleable.CalenderCustom_font_calender)?:""
            ,getInteger(R.styleable.CalenderCustom_gravity_text_date,0))
            recycle()
        }
    }

    private fun getSwitchTypeFromAttr(attributes: TypedArray): TypeCalenderCustom =
        when (attributes.getInt(R.styleable.CalenderCustom_theme_calender, 1)) {
            2 -> TypeCalenderCustom.CalenderMarkColor
            3 -> TypeCalenderCustom.CalenderDetailed
            else -> TypeCalenderCustom.DefaultCalender
        }

    private fun inflateView(
        type: TypeCalenderCustom,
        titleColor: Int,
        nameDayColor: Int,
        markDayColor: ColorStateList?,
        markTextDayColor: Int,
        dayCalenderColor: Int,
        titleSize: Float,
        nameDaySize: Float,
        dayCalenderSize: Float,
        visibilityLineNameDay: Int,
        startWeekCalender: Int,
        colorLine: Int,
        sizeLine: Int,
        statusSatSunColorBar: Boolean,
        colorSatSunBar: ColorStateList?,
        drawableButtonBack: Drawable?,
        drawableButtonNext: Drawable?,
        buttonBackSize: Float,
        buttonNextSize: Float,
        fontCalender: String,
        gravity : Int
    ) {
        manager = CalenderFactory.build(type)
        removeAllViews()
        manager.initViewCalender(
            context,
            this,
            titleColor,
            nameDayColor,
            markDayColor ?: resources.getColorStateList(R.color.orange_new),
            dayCalenderColor,
            markTextDayColor,
            titleSize,
            nameDaySize,
            dayCalenderSize, visibilityLineNameDay, startWeekCalender,
            formatter, Calendar.getInstance().time, colorLine, sizeLine,
            statusSatSunColorBar,
            colorSatSunBar ?: resources.getColorStateList(R.color.bg),
            drawableButtonBack ?: resources.getDrawable(R.drawable.baseline_arrow_back_ios_24),
            drawableButtonNext ?: resources.getDrawable(R.drawable.baseline_arrow_forward_ios_24),
            buttonBackSize,
            buttonNextSize,
            fontCalender,
            gravity
        )
    }

    private fun spToPx(sp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun setOnClickCalender(callBack: ((Date) -> Unit)) {
        manager.setOnClickListener {
            callBack.invoke(it)
        }
    }

    fun setCalender(calender: Date) {
        manager.setCalender(calender)
    }

    fun setTitleSize(sizeText: Float) {
        manager.setTitleSize(sizeText)
    }

    fun setNameDaySize(sizeText: Float) {
        manager.setNameDaySize(sizeText)
    }

    fun setDayCalenderSize(sizeText: Float) {
        manager.setDayCalenderSize(sizeText)
    }


    fun setTitleColor(titleColor: Int) {
        manager.setTitleColor(titleColor)
    }

    fun setNameDayColor(nameDayColor: Int) {
        manager.setNameDayColor(nameDayColor)
    }

    fun setMarkDayColor(markDayColor: ColorStateList) {
        manager.setMarkDayColor(markDayColor)
    }

    fun setDayCalenderColor(dayCalenderColor: Int) {
        manager.setDayCalenderColor(dayCalenderColor)
    }

    fun setMarkTextDayColor(markTextDayColor: Int) {
        manager.setMarkTextDayColor(markTextDayColor)
    }


    //    fun setTintButtonNextAndBack(colorTint: Int) {
//        btnBack?.let {
//            it.imageTintList = resources.getColorStateList(colorTint)
//        }
//        btnNext?.let {
//            it.imageTintList = resources.getColorStateList(colorTint)
//        }
//    }
    fun setColorBackgroundToday(colorBackgroundToday: Int) {
        manager.setColorBackgroundToday(colorBackgroundToday)
    }

    fun setColorTextToday(colorTextToday: Int) {
        manager.setColorTextToday(colorTextToday)
    }

    fun setDataCalender(mHashMap: ArrayList<HashMap<String, Any>>) {
        manager.setDataCalender(mHashMap)
    }

    fun setStartDayOfWeek(startWeek: Int) {
        manager.setStartDayOfWeek(startWeek)
    }

    fun setOnClickButtonBackAndNextCalender(callBack: (Date) -> Unit) {
        manager.setOnClickButtonBackAndNextCalender {
            callBack.invoke(it)
        }
    }


    fun setFormatterCalender(simpleDateFormat: SimpleDateFormat) {
        manager.setFormatterCalender(simpleDateFormat)
    }

    fun setLineNameColor(color: Int) {
        manager.setLineNameColor(color)
    }

    fun setLineNameSize(size: Int) {
        manager.setLineNameSize(size)
    }

    fun setVisibilityLineNameDay(visibility: Int) {
        manager.setVisibilityLineNameDay(visibility)
    }

}