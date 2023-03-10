package com.example.calendar_custom_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.calendar_custom_library.viewCalender.CalenderCustom
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    var c= Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thaiCalendar = Calendar.getInstance(Locale("th", "TH"))
        Log.i("checkDate",thaiCalendar.get(Calendar.YEAR).toString())

        val t = findViewById<TextView>(R.id.text01)
        t.setOnClickListener {

        }
        val x = findViewById<CalenderCustom>(R.id.test)

        x.setFormatterCalender(SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH")))
        var formatter = SimpleDateFormat("dd MMMM yyyy", Locale("th", "TH"))
        x.setOnClickCalender {
            Log.i("checkDate",formatter.format(it))
        }
        x.setOnClickButtonBackAndNextCalender {
            Log.i("checkDate",formatter.format(it))
        }

    }
    private fun convertStringCalendar(dateTime: String): Date {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        return  sdf.parse(dateTime)
    }
}