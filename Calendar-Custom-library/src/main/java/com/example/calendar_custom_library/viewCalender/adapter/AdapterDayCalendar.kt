package com.example.calendar_custom_library.viewCalender.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.event.EventAdapter
import java.util.*

class AdapterDayCalendar(
    private val mContext: Context,
    private val dayData: MutableList<Date>,
    private val sizeText: Float,
    private val calenderShowView: Calendar,
    private val clickCalendar: Calendar?,
    private val colorTextDay: Int,
    private val colorMarkDay: ColorStateList,
    private val colorTextMarkDay: Int,
    private val statusSatSunColorBar: Int,
    private val colorSatSunBar: ColorStateList,
    private val customFont: Typeface?,
    private val callBack: ((Date) -> Unit)
) : RecyclerView.Adapter<ViewHolderItemDayCalendar>() {
    private lateinit var mEventAdapter: EventAdapter
    private var date: Date? = null
    private val dateToDay = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemDayCalendar {
        mEventAdapter = EventAdapter(mContext)
        return ViewHolderItemDayCalendar(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_day, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderItemDayCalendar, position: Int) {
        customFont?.let {
            holder.textNameDay.typeface = customFont
        }


        date = dayData[position]
        val dateSetModel = Calendar.getInstance()
        dateSetModel.time = date
        val daySetModel = dateSetModel[Calendar.DAY_OF_MONTH]
        val monthSetModel = dateSetModel[Calendar.MONTH]
        val yearSetModel = dateSetModel[Calendar.YEAR]

        holder.textNameDay.text = daySetModel.toString()
        holder.textNameDay.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeText)
        holder.textNameDay.setTextColor(
            if (mEventAdapter.checkDayInMonth(
                    monthSetModel,
                    yearSetModel, calenderShowView
                )
            ) colorTextDay else mContext.resources.getColor(R.color.gray_arrow)
        )


        if (clickCalendar != null && mEventAdapter.checkDayClick(daySetModel, monthSetModel, yearSetModel, clickCalendar)) {
            holder.layoutDay.backgroundTintList = colorMarkDay
            holder.textNameDay.setTextColor(colorTextMarkDay)
        } else {
            holder.layoutDay.backgroundTintList =
                mContext.resources.getColorStateList(R.color.clear_color)
        }

        if (statusSatSunColorBar!=2) {
            mEventAdapter.checkSatAndSun(dateSetModel, position, statusSatSunColorBar)?.let {
                holder.itemView.background = it
                holder.itemView.backgroundTintList = colorSatSunBar
            }
        }
        if (mEventAdapter.checkDateToDay(
                daySetModel,
                monthSetModel,
                yearSetModel,
                dateToDay,
                clickCalendar
            )
        ) {
            holder.textNameDay.setTextColor(colorMarkDay)
        }

        setEventClick(holder, position)
    }

    private fun setEventClick(holder: ViewHolderItemDayCalendar, position: Int) {
        holder.itemView.setOnClickListener {
            callBack.invoke(dayData[position])
        }
    }

    override fun getItemCount() = dayData.size
}


class ViewHolderItemDayCalendar(item: View) : RecyclerView.ViewHolder(item) {
    val layoutDay: LinearLayout = item.findViewById(R.id.layoutDay)
    val textNameDay: TextView = item.findViewById(R.id.textNameDay)
}