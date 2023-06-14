package com.example.calendar_custom_library.viewCalender.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar_custom_library.R
import com.example.calendar_custom_library.event.EventAdapter
import java.util.*

class AdapterDayCalenderColorStatus(
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
    private val mHashMap: ArrayList<HashMap<String, Any>>,
    private val customFont: Typeface?,
    private val positionLayoutStatusDay: Boolean = true,
    private val sizeLayoutStatusDay: Float = 10f,
    private val callBack: ((Date) -> Unit)
) : RecyclerView.Adapter<ViewHolderCalenderColorStatus>() {
    private lateinit var mEventAdapter: EventAdapter
    private var date: Date? = null
    private val dateToDay = Calendar.getInstance()

    private var calenderHashMap = Calendar.getInstance()
    private var dateHashMap: Date? = null
    private var colorStatus: ColorStateList? = null
    private var textStatus = ""
    private var colorTextDayStatus: ColorStateList? = null
    private var statusClickDay: Boolean? = null
    private val density = mContext.resources.displayMetrics.density

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderCalenderColorStatus {
        mEventAdapter = EventAdapter(mContext)
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.calender_color_status, parent, false)
        return ViewHolderCalenderColorStatus(itemView)
    }

    override fun getItemCount() = dayData.size

    override fun onBindViewHolder(holder: ViewHolderCalenderColorStatus, position: Int) {
        customFont?.let {
            holder.textNameDay.typeface = customFont
            holder.textStatus.typeface = customFont
        }

        val topLayoutParams: ViewGroup.LayoutParams = holder.statusDayTop.layoutParams
        topLayoutParams.width = (sizeLayoutStatusDay * density).toInt() // Width in dp
        topLayoutParams.height = (sizeLayoutStatusDay * density).toInt() // Height in dp
        holder.statusDayTop.layoutParams = topLayoutParams

        val bottomLayoutParams: ViewGroup.LayoutParams = holder.statusDayBottom.layoutParams
        bottomLayoutParams.width = (sizeLayoutStatusDay * density).toInt() // Width in dp
        bottomLayoutParams.height = (sizeLayoutStatusDay * density).toInt() // Height in dp
        holder.statusDayBottom.layoutParams = bottomLayoutParams

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


        if (clickCalendar != null && mEventAdapter.checkDayClick(
                daySetModel,
                monthSetModel,
                yearSetModel,
                clickCalendar
            )
        ) {
            holder.layoutDay.backgroundTintList = colorMarkDay
            holder.textNameDay.setTextColor(colorTextMarkDay)
        } else {
            holder.layoutDay.backgroundTintList =
                mContext.resources.getColorStateList(R.color.clear_color)
        }

        if (statusSatSunColorBar != 2) {
            mEventAdapter.checkSatAndSun(dateSetModel, position, statusSatSunColorBar)?.let {
                holder.itemView.background = it
                holder.itemView.backgroundTintList = colorSatSunBar
            }
        }

        if (mEventAdapter.checkDateToDay(
                daySetModel, monthSetModel, yearSetModel, dateToDay,
                clickCalendar
            )
        ) {
            holder.textNameDay.setTextColor(colorMarkDay)
        }
        holder.statusDayTop.visibility = View.GONE
        holder.statusDayBottom.visibility = View.GONE
        holder.textStatus.visibility = View.GONE

        mHashMap.map { data ->
            data.keys.map {
                checkDataHashMap(data, it)
            }
            dateHashMap?.let { dateHashMap ->
                calenderHashMap.time = dateHashMap
                if (checkDayStatus(calenderHashMap, daySetModel, monthSetModel, yearSetModel)) {
                    if (textStatus != "") {
                        holder.statusDayTop.visibility = View.GONE
                        holder.textStatus.visibility = View.VISIBLE
                        holder.textStatus.text = textStatus
                        holder.textStatus.setTextColor(colorStatus)
                    } else {
                        if (positionLayoutStatusDay) {
                            holder.statusDayTop.visibility = View.VISIBLE
                            holder.statusDayBottom.visibility = View.GONE
                        } else {
                            holder.statusDayTop.visibility = View.GONE
                            holder.statusDayBottom.visibility = View.VISIBLE
                        }
                        holder.textStatus.visibility = View.GONE
                        holder.statusDayTop.backgroundTintList = colorStatus
                        holder.statusDayBottom.backgroundTintList = colorStatus
                    }
                    colorTextDayStatus?.let {
                        holder.textNameDay.setTextColor(it)
                    }
                    statusClickDay?.let {
                        holder.itemView.isEnabled = it
                        holder.textNameDay.setTextColor(mContext.resources.getColor(R.color.gray_arrow))
                    }
                } else {
                    holder.itemView.isEnabled = true
                }
                this.statusClickDay = null
                this.dateHashMap = null
                this.colorStatus = null
                this.textStatus = ""
                this.colorTextDayStatus = null
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
            is ColorStateList -> {
                if (colorStatus == null) {
                    colorStatus = data[key] as ColorStateList
                } else if (colorTextDayStatus == null) {
                    colorTextDayStatus = data[key] as ColorStateList
                }

            }
            is String -> {
                textStatus = data[key] as String
            }
            is Boolean -> {
                statusClickDay = data[key] as Boolean
            }
        }
    }


    private fun setEventClick(holder: ViewHolderCalenderColorStatus, position: Int) {
        holder.itemView.setOnClickListener {
            callBack.invoke(dayData[position])
        }
    }


}


class ViewHolderCalenderColorStatus(item: View) : RecyclerView.ViewHolder(item) {
    val layoutDay: TextView = item.findViewById(R.id.layoutDay)
    val statusDayTop: TextView = item.findViewById(R.id.statusDayTop)
    val statusDayBottom: TextView = item.findViewById(R.id.statusDayBottom)
    val textStatus: TextView = item.findViewById(R.id.textStatus)
    val textNameDay: TextView = item.findViewById(R.id.textNameDay)
}