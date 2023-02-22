package com.example.calendar_custom_library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.calendar_custom_library.viewCalender.CalenderCustom

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val x = findViewById<CalenderCustom>(R.id.test)
        x.setOnClickCalender {
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}