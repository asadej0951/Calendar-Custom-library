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
    var c= Calendar.getInstance()
    val test = ArrayList<HashMap<String,Any> >()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thaiCalendar = Calendar.getInstance(Locale("th", "TH"))
        Log.i("checkDate",thaiCalendar.get(Calendar.YEAR).toString())

        val a : HashMap<String,Any> = java.util.HashMap()
        val b : HashMap<String,Any> = java.util.HashMap()
        val c : HashMap<String,Any> = java.util.HashMap()
        a["test"] = convertStringCalendar("12 04 2023")
        a["double"] = resources.getColorStateList(R.color.orange_new)

        b["test"] = convertStringCalendar("17 04 2023")
        b["double"] = resources.getColorStateList(R.color.orange_new)

        c["test"] = convertStringCalendar("15 04 2023")
        c["double"] = resources.getColorStateList(R.color.orange_new)




        test.add(a)
        test.add(b)
        test.add(c)



        val t = findViewById<TextView>(R.id.text01)
        t.setOnClickListener {

        }
        val x = findViewById<CalenderCustom>(R.id.test)

        x.setColorBackgroundToday(resources.getColor(R.color.purple_200))
        x.setColorTextToday(resources.getColor(R.color.purple_500))
        x.setDataCalender(test)
        x.setFormatterCalender(SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH")))

        x.setOnClickButtonBackAndNextCalender {
            test.clear()
            val b : HashMap<String,Any> = java.util.HashMap()
            val c : HashMap<String,Any> = java.util.HashMap()
            a["test"] = convertStringCalendar("02 05 2023")
            a["double"] = resources.getColorStateList(R.color.orange_new)
            c["test"] = convertStringCalendar("07 05 2023")
            c["double"] = resources.getColorStateList(R.color.orange_new)
            c["sss"] = "9999"
            test.add(a)
            test.add(c)
            x.setDataCalender(test)
        }

//        x.setFormatterCalender(SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH")))
//        var formatter = SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH"))
//        x.setOnClickCalender {
//            Log.i("checkDate",formatter.format(it))
//        }
//        x.setOnClickButtonBackAndNextCalender {
//            Log.i("checkDate",formatter.format(it))
//        }

    }

    private fun convertStringCalendar(dateTime: String): Date {
        val sdf = SimpleDateFormat("dd MM yyyy", Locale.ENGLISH)
        return  sdf.parse(dateTime)
    }
}