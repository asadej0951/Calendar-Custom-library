package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import java.util.*

class AdapterDayCalendar(
    private val mContext: Context,
    private val dayData: MutableList<Date>,
    private val sizeText: Float,
    private val calenderShowView: Calendar,
    private val clickCalendar: Calendar,
    private val colorTextDay: Int,
    private val colorMarkDay: Int,
    private val colorTextMarkDay: Int,
    private val callBack: ((Date) -> Unit)
) : RecyclerView.Adapter<ViewHolderItemDayCalendar>() {
    private var date: Date? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemDayCalendar {

        return ViewHolderItemDayCalendar(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_day, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolderItemDayCalendar, position: Int) {
        date = dayData[position]
        val dateSetModel = Calendar.getInstance()
        dateSetModel.time = date
        val daySetModel = dateSetModel[Calendar.DAY_OF_MONTH]
        val monthSetModel = dateSetModel[Calendar.MONTH]
        val yearSetModel = dateSetModel[Calendar.YEAR]

        holder.textNameDay.text = daySetModel.toString()
        holder.textNameDay.textSize = sizeText
        holder.textNameDay.setTextColor(
            mContext.resources.getColor(
                if (checkDayInMonth(
                        monthSetModel,
                        yearSetModel
                    )
                ) colorTextDay else R.color.gray_arrow
            )
        )

        if (checkToDay(daySetModel, monthSetModel, yearSetModel)) {
            holder.layoutDay.backgroundTintList =
                mContext.resources.getColorStateList(colorMarkDay)
            holder.textNameDay.setTextColor(mContext.resources.getColor(colorTextMarkDay))
        } else {
            holder.layoutDay.backgroundTintList =
                mContext.resources.getColorStateList(R.color.clear_color)
        }
        setEventClick(holder, position)
    }

    private fun setEventClick(holder: ViewHolderItemDayCalendar, position: Int) {
        holder.itemView.setOnClickListener {
            callBack.invoke(dayData[position])
        }
    }

    private fun checkToDay(daySetModel: Int, monthSetModel: Int, yearSetModel: Int): Boolean {
        return clickCalendar.get(Calendar.DAY_OF_MONTH) == daySetModel && clickCalendar.get(
            Calendar.MONTH
        ) == monthSetModel && clickCalendar.get(Calendar.YEAR) == yearSetModel
    }

    private fun checkDayInMonth(monthSetModel: Int, yearSetModel: Int): Boolean {
        return calenderShowView.get(Calendar.MONTH) == monthSetModel && calenderShowView.get(
            Calendar.YEAR
        ) == yearSetModel

    }

    override fun getItemCount() = dayData.size
}


class ViewHolderItemDayCalendar(item: View) : RecyclerView.ViewHolder(item) {
    val layoutDay: LinearLayout = item.findViewById(R.id.layoutDay)
    val textStatus: TextView = item.findViewById(R.id.textStatus)
    val textNameDay: TextView = item.findViewById(R.id.textNameDay)
}