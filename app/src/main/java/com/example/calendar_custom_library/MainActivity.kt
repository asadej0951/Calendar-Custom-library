package com.example.calendar_custom_library

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calendar_custom_library.viewCalender.CalenderCustom
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {
    private var mCalendar = Calendar.getInstance()
    private val test = ArrayList<HashMap<String, Any>>()
    private val f = SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thaiCalendar = Calendar.getInstance(Locale("th", "TH"))
        Log.i("checkDate", thaiCalendar.get(Calendar.YEAR).toString())
        mCalendar.set(2023, 7, 15)

        val a: HashMap<String, Any> = java.util.HashMap()
        val b: HashMap<String, Any> = java.util.HashMap()
        val c: HashMap<String, Any> = java.util.HashMap()
        a["test"] = convertStringCalendar("12 06 2023")
        a["double"] = resources.getColorStateList(R.color.text_font_black)
        a["text"] = ""

        b["test"] = convertStringCalendar("17 06 2023")
        b["double"] = resources.getColorStateList(R.color.text_font_black)
        b["message"] = ""


        c["test"] = convertStringCalendar("15 06 2023")
        c["double"] = resources.getColorStateList(R.color.text_font_black)
        c["data"] = ""




        test.add(a)
        test.add(b)
        test.add(c)


        val t = findViewById<TextView>(R.id.text01)
        t.setOnClickListener {

        }
        val calenderCustom = findViewById<CalenderCustom>(R.id.test)

        calenderCustom.setColorBackgroundToday(resources.getColor(R.color.purple_200))
        calenderCustom.setColorTextToday(resources.getColor(R.color.purple_500))
        calenderCustom.setSizeStatusDay(5f)
        calenderCustom.setPositionLayoutStatusDay(false)
        calenderCustom.setDataCalender(test)
//        x.setCalender( convertStringCalendar("15 05 2023"))
//        x.setCalender(mCalendar.time)
//        x.setFormatterCalender(SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH")))
        calenderCustom.setFormatterCalender("dd MMMM yyyy",Locale("th"))
//        x.setFormatterCalender("dd MMMM yyyy", Locale.US)

        calenderCustom.setOnClickButtonBackAndNextCalender {
//            test.clear()
//            val b: HashMap<String, Any> = java.util.HashMap()
//            val c: HashMap<String, Any> = java.util.HashMap()
//            a["test"] = convertStringCalendar("02 05 2023")
//            a["double"] = resources.getColorStateList(R.color.orange_new)
//            c["test"] = convertStringCalendar("07 05 2023")
//            c["double"] = resources.getColorStateList(R.color.orange_new)
//            c["sss"] = "9999"
//            test.add(a)
//            test.add(c)
//            x.setDataCalender(test)
        }
        calenderCustom.setOnClickCalender {
            Log.i("checkDateTime", f.format(it))
        }
    }

    private fun convertStringCalendar(dateTime: String): Date {
        val sdf = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
        return sdf.parse(dateTime)
    }
}