package com.example.calendar_custom_library.viewCalender

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import java.util.*

class AdapterDayCalendar(
    private val mContext: Context,
    private val dayData: MutableList<Date>
) : RecyclerView.Adapter<ViewHolderItemDayCalendar>() {
    private var date: Date? = null
    private val currentCalendar = Calendar.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItemDayCalendar {

        return ViewHolderItemDayCalendar(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_day,parent,false
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
        holder.textNameDay.setTextColor(
            mContext.resources.getColor(
                if (checkDayInMonth(
                        monthSetModel,
                        yearSetModel
                    )
                ) R.color.text_font_black else R.color.gray_arrow
            )
        )

        if (checkToDay(daySetModel, monthSetModel, yearSetModel)) {
            holder.layoutDay.backgroundTintList =
                mContext.resources.getColorStateList(R.color.orange_new)
            holder.textNameDay.setTextColor(mContext.resources.getColor(R.color.white_pressed))
        }
    }

    private fun checkToDay(daySetModel: Int, monthSetModel: Int, yearSetModel: Int): Boolean {
        return currentCalendar.get(Calendar.DAY_OF_MONTH) == daySetModel && currentCalendar.get(
            Calendar.MONTH
        ) == monthSetModel && currentCalendar.get(Calendar.YEAR) == yearSetModel
    }

    private fun checkDayInMonth(monthSetModel: Int, yearSetModel: Int): Boolean {
        return currentCalendar.get(Calendar.MONTH) == monthSetModel && currentCalendar.get(Calendar.YEAR) == yearSetModel

    }

    override fun getItemCount() = dayData.size
}


class ViewHolderItemDayCalendar(item: View):RecyclerView.ViewHolder(item){
    val layoutDay : LinearLayout = item.findViewById(R.id.layoutDay)
    val textStatus : TextView = item.findViewById(R.id.textStatus)
    val textNameDay : TextView = item.findViewById(R.id.textNameDay)
}