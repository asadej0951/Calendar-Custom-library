package com.example.calendar_custom_library.viewCalender.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.event.EventAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class AdapterDetailedCalendar(
    private val mContext: Context,
    private val dayData: MutableList<Date>,
    private val sizeText: Float,
    private val calenderShowView: Calendar,
    private val clickCalendar: Calendar,
    private val colorTextDay: Int,
    private val colorMarkDay: ColorStateList,
    private val colorTextMarkDay: Int,
    private val colorBackgroundToday :Int,
    private val colorTextToday :Int,
    private val mHashMap: ArrayList<HashMap<String, Any>>,
    private val customFont: Typeface?,
    private val callBack: ((Date) -> Unit)
) : RecyclerView.Adapter<ViewHolderDetailedCalendar>() {

    private lateinit var mEventAdapter: EventAdapter
    private var date: Date? = null
    private val dateToDay = Calendar.getInstance()

    private var calenderHashMap = Calendar.getInstance()
    private var dateHashMap: Date? = null
    private var textStatus = ArrayList<String>()
    private var textColorDetailed = 0
    private var maxSizeDateHashMap = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetailedCalendar {
        mEventAdapter = EventAdapter(mContext)
        return ViewHolderDetailedCalendar(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_calender_detailed, parent, false
            )
        )
    }

    override fun getItemCount() = dayData.size

    override fun onBindViewHolder(holder: ViewHolderDetailedCalendar, position: Int) {
        customFont?.let {
            holder.textNameDay.typeface = customFont
        }
        textColorDetailed = colorTextDay
        date = dayData[position]
        val dateSetModel = Calendar.getInstance()
        dateSetModel.time = date
        val daySetModel = dateSetModel[Calendar.DAY_OF_MONTH]
        val monthSetModel = dateSetModel[Calendar.MONTH]
        val yearSetModel = dateSetModel[Calendar.YEAR]

        holder.textNameDay.text = daySetModel.toString()
        holder.textNameDay.textSize = sizeText
        holder.textNameDay.setTextColor(
            if (mEventAdapter.checkDayInMonth(
                    monthSetModel,
                    yearSetModel, calenderShowView
                )
            ) colorTextDay else mContext.resources.getColor(R.color.gray_arrow)
        )


        if (mEventAdapter.checkToDay(daySetModel, monthSetModel, yearSetModel, clickCalendar)) {
            holder.layoutDay.setBackgroundColor(colorMarkDay.defaultColor)
            holder.textNameDay.setTextColor(colorTextMarkDay)
            textColorDetailed = colorTextMarkDay
        } else {
            holder.layoutDay.setBackgroundColor(mContext.resources.getColor(R.color.clear_color))
        }
        if (mEventAdapter.checkDateToDay(
                daySetModel, monthSetModel, yearSetModel, dateToDay,
                clickCalendar
            )
        ) {
            holder.layoutDay.setBackgroundColor(colorBackgroundToday)
            holder.textNameDay.setTextColor(colorTextToday)
            textColorDetailed = colorTextToday
        }

        holder.layoutDetailed.removeAllViews()
        val textView = AppCompatTextView(mContext)
        val params = LinearLayout.LayoutParams(
            LinearLayoutCompat.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1f
        )
        textView.layoutParams = params
        textView.text = "text"
        textView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
        textView.textSize = sizeText - 2f
        textView.visibility = View.INVISIBLE
        holder.layoutDetailed.addView(textView)

        mHashMap.map { data ->
            data.keys.map {
                checkDataHashMap(data, it)
            }
            dateHashMap?.let { dateHashMap ->
                calenderHashMap.time = dateHashMap
                if (checkDayStatus(calenderHashMap, daySetModel, monthSetModel, yearSetModel)) {
                    holder.layoutDetailed.removeAllViews()
                    if (textStatus.size != 0) {
                        textStatus.map { textStatus ->
                            val tvNameDay = AppCompatTextView(mContext)
                            val params = LinearLayout.LayoutParams(
                                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                            )
                            customFont?.let {
                                tvNameDay.typeface = customFont
                            }
                            tvNameDay.layoutParams = params
                            tvNameDay.text = textStatus
                            tvNameDay.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                            tvNameDay.setTextColor(textColorDetailed)
                            tvNameDay.textSize = sizeText - 2f
                            holder.layoutDetailed.addView(tvNameDay)
                        }
                    }
                }
                this.dateHashMap = null
                this.textStatus.clear()
            }

        }
        setEventClick(holder, position)
    }

    private fun checkDayStatus(
        calenderHashMap: Calendar,
        daySetModel: Int,
        monthSetModel: Int,
        yearSetModel: Int
    ): Boolean {
        return calenderHashMap[Calendar.DAY_OF_MONTH] == daySetModel && calenderHashMap[Calendar.MONTH] == monthSetModel && calenderHashMap[Calendar.YEAR] == yearSetModel
    }

    private fun checkDataHashMap(data: HashMap<String, Any>, key: String) {
        when (data[key]) {
            is Date -> {
                dateHashMap = data[key] as Date
            }
            is String -> {
                textStatus.add(data[key] as String)
            }
            is ArrayList<*> -> {
                val arrayList = data[key] as ArrayList<*>
                textStatus.addAll(arrayList as ArrayList<String>)
            }
        }
    }

    private fun setEventClick(holder: ViewHolderDetailedCalendar, position: Int) {
        holder.itemView.setOnClickListener {
            callBack.invoke(dayData[position])
        }
    }
}

class ViewHolderDetailedCalendar(item: View) : RecyclerView.ViewHolder(item) {
    val textNameDay: TextView = item.findViewById(R.id.textNameDay)
    val layoutDay: LinearLayout = item.findViewById(R.id.layoutDay)
    val layoutDetailed: LinearLayout = item.findViewById(R.id.layoutDetailed)
}