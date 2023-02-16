package com.example.calendar_custom_library.viewCalender

import android.content.Context
import java.util.*

class DateTimeManager constructor(private var context: Context) {
    fun setDateTime():String{
        val day = "${Calendar.getInstance().get(Calendar.DATE)}"
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val year = Calendar.getInstance().get(Calendar.YEAR)

        var yearShow = year + 543
        var monthShow = ""
        when (month) {
            1 -> monthShow = "มกราคม"
            2 -> monthShow = "กุมภาพันธ์"
            3 -> monthShow = "มีนาคม"
            4 -> monthShow = "เมษายน"
            5 -> monthShow = "พฤษภาคม"
            6 -> monthShow = "มิถุนายน"
            7 -> monthShow = "กรกฎาคม"
            8 -> monthShow = "สิงหาคม"
            9 -> monthShow = "กันยายน"
            10 -> monthShow = "ตุลาคม"
            11 -> monthShow = "พฤศจิกายน"
            12 -> monthShow = "ธันวาคม"
        }
        return "$day $monthShow $yearShow"
    }
}