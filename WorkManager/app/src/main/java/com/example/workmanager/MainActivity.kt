package com.example.workmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import java.util.*
import kotlinx.coroutines.Dispatchers

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

class MainActivity : AppCompatActivity() {
    private lateinit var city1: EditText
    private lateinit var city2: EditText

    private lateinit var textViewWeather1: TextView
    private lateinit var textViewWeather2: TextView

    private lateinit var textViewWind1: TextView
    private lateinit var textViewWind2: TextView

    private lateinit var cityName1: TextView
    private lateinit var cityName2: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        city1 = findViewById(R.id.editTextCity)
        city2 = findViewById(R.id.editTextCity2)

        cityName1 = findViewById(R.id.cityName1)
        cityName2 = findViewById(R.id.cityName2)

        textViewWeather1 = findViewById(R.id.textViewWeather1)
        textViewWeather2 = findViewById(R.id.textViewWeather2)
        textViewWind1 = findViewById(R.id.textViewWind1)
        textViewWind2 = findViewById(R.id.textViewWind2)

        val buttonRefresh = findViewById<Button>(R.id.buttonRefresh)
        buttonRefresh.setOnClickListener {
            onClick(it)
        }
    }

    fun onClick(view: View) {
        val cityNameText = city1.text.toString()
        val cityNameText2 = city2.text.toString()

        if (cityNameText.isEmpty() || cityNameText2.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Enter two cities!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            fetchWeatherData(cityNameText) { weatherData ->
                cityName1.text = "$cityNameText"
                textViewWeather1.text = "Weather: ${weatherData.weather[0].description}"
                textViewWind1.text = "Wind: ${weatherData.wind.speed}"
            }
            fetchWeatherData(cityNameText2) { weatherData ->
                cityName2.text = "$cityNameText2"
                textViewWeather2.text = "Weather: ${weatherData.weather[0].description}"
                textViewWind2.text = "Wind: ${weatherData.wind.speed}"
            }
        }
    }

    private fun fetchWeatherData(cityName: String, callback: (WeatherData) -> Unit) {
        val API_KEY = getString(R.string.api_key)
        val weatherURL =
            "https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY&units=metric"

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val stream = URL(weatherURL).openStream()
                val data = stream.bufferedReader().use { it.readText() }
                val weatherData = Gson().fromJson(data, WeatherData::class.java)
                launch(Dispatchers.Main) {
                    callback(weatherData)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    "There is no such city!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

