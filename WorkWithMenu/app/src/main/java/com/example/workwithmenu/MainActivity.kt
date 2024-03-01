package com.example.workwithmenu

import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    data class WeatherData(
        @SerializedName("sys") val sys: Sys,
        @SerializedName("name") val name: String,
        @SerializedName("weather") val weather: List<Weather>,
        @SerializedName("wind") val wind: Wind
    )

    data class Sys(
        @SerializedName("sunrise") val sunrise: Long,
        @SerializedName("sunset") val sunset: Long
    )

    data class Weather(
        @SerializedName("description") val description: String
    )

    data class Wind(
        @SerializedName("speed") val speed: Double
    )

    lateinit var toolbar: Toolbar
    lateinit var buttonRefresh: Button
    lateinit var textViewSunrise: TextView
    lateinit var textViewSunset: TextView
    lateinit var textViewDescription: TextView
    lateinit var textViewWind: TextView

    lateinit var sunriseAndroidText: CharSequence
    lateinit var sunsetAndroidText: CharSequence
    lateinit var descriptionAndroidText: CharSequence
    lateinit var windAndroidText: CharSequence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        textViewSunrise = findViewById<TextView>(R.id.textViewSunrise)
        textViewSunset = findViewById<TextView>(R.id.textViewSunset)
        textViewDescription = findViewById<TextView>(R.id.textViewDescription)
        textViewWind = findViewById<TextView>(R.id.textViewWind)

        sunriseAndroidText = textViewSunrise.text
        sunsetAndroidText = textViewSunset.text
        descriptionAndroidText = textViewDescription.text
        windAndroidText = textViewWind.text

        buttonRefresh = findViewById<Button>(R.id.buttonRefresh)
        buttonRefresh.setOnClickListener {
            onClick(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.lang_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.en_lang -> {
                Log.i("mytag", "en")
                setLocale("en")
                true
            }

            R.id.ru_lang -> {
                Log.i("mytag", "ru")
                setLocale("ru")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)
        baseContext.resources.updateConfiguration(
            configuration,
            baseContext.resources.displayMetrics
        )
        recreate()
    }


    fun onClick(view: View) {
        val cityName = findViewById<EditText>(R.id.editTextCity).text.toString()

        //очищаем от предыдущего текста
        textViewSunrise.text = sunriseAndroidText
        textViewSunset.text = sunsetAndroidText
        textViewDescription.text = descriptionAndroidText
        textViewWind.text = windAndroidText

        if (cityName == "") {
            Toast.makeText(
                applicationContext,
                getString(R.string.emptyText), Toast.LENGTH_SHORT
            ).show()
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                loadWeather()
            }
        }
    }

    private suspend fun loadWeather() {
        val API_KEY = getString(R.string.api_key)
        val cityName = findViewById<EditText>(R.id.editTextCity).text.toString()

        val weatherURL =
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY&units=metric"
        val stream = URL(weatherURL).openStream()
        val data = Scanner(stream).useDelimiter("\\A").next()
        val weatherData = Gson().fromJson(data, WeatherData::class.java)
        Log.d("mytag", weatherData.toString())
        withContext(Dispatchers.Main) {
            updateUI(weatherData)
        }

    }

    private fun updateUI(weatherData: WeatherData) {
        Log.d("mytag", weatherData.toString())

        val sunriseTime = convertTime(weatherData.sys.sunrise)
        val sunsetTime = convertTime(weatherData.sys.sunset)
        val description = weatherData.weather[0].description
        val wind = weatherData.wind.speed.toString()

        textViewSunrise.text = "${textViewSunrise.text} $sunriseTime"
        textViewSunset.text = "${textViewSunset.text} $sunsetTime"
        textViewDescription.text = "${textViewDescription.text} $description"
        textViewWind.text = "${textViewWind.text} $wind"
    }

    private fun convertTime(unixTimestamp: Long): String {
        val date = Date(unixTimestamp * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}