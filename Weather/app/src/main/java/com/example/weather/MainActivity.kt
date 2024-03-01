package com.example.weather

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //detailedWeatherFragment()
    }

    fun briefWeatherFragment(view: View) {
        Log.d("mytag", "brief")
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, BriefWeatherFragment())
            commit()
        }
    }

    fun detailedWeatherFragment(view: View) {
        Log.d("mytag", "detail")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, DetailedWeatherFragment())
            .commit()
    }


}