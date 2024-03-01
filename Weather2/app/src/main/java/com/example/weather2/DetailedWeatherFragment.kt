package com.example.weather2

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Scanner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DetailedWeatherFragment : Fragment() {

    data class WeatherData(
        @SerializedName("sys") val sys: Sys,
        @SerializedName("name") val name: String,
        @SerializedName("weather") val weather: List<Weather>,
        @SerializedName("wind") val wind: Wind,
        @SerializedName("main") val main: Main
    )

    data class Sys(
        @SerializedName("sunrise") val sunrise: Long,
        @SerializedName("sunset") val sunset: Long
    )

    data class Main(
        @SerializedName("temp") val temp: Double,
    )

    data class Weather(
        @SerializedName("description") val description: String
    )

    data class Wind(
        @SerializedName("speed") val speed: Double
    )

    private lateinit var textViewTemp: TextView
    private lateinit var textViewSunrise: TextView
    private lateinit var textViewSunset: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var textViewWind: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detailed_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        textViewTemp = view.findViewById<TextView>(R.id.textViewTemp)
        textViewSunrise = view.findViewById<TextView>(R.id.textViewSunrise)
        textViewSunset = view.findViewById<TextView>(R.id.textViewSunset)
        textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        textViewWind = view.findViewById<TextView>(R.id.textViewWind)
        GlobalScope.launch(Dispatchers.IO) {
            loadWeather()
        }
    }

    private suspend fun loadWeather() {
        val API_KEY = getString(R.string.api_key)

        val weatherURL =
            "https://api.openweathermap.org/data/2.5/weather?q=London&appid=$API_KEY&units=metric"
        val stream = URL(weatherURL).openStream()

        val data = Scanner(stream).useDelimiter("\\A").next()
        val weatherData = Gson().fromJson(data, WeatherData::class.java)

        withContext(Dispatchers.Main) {
            updateUI(weatherData)
        }
    }

    private fun updateUI(weatherData: WeatherData) {
        Log.d("mytag", weatherData.toString())
        val sunriseTime = weatherData.sys.sunrise.let { convertTime(it) }
        val sunsetTime = weatherData.sys.sunset.let { convertTime(it) }
        val description = weatherData.weather[0].description
        val wind = weatherData.wind.speed.toString()
        val temp = weatherData.main.temp.toString()

        textViewTemp.text = "Temperature: $temp"
        textViewSunrise.text = "Sunrise Time: $sunriseTime"
        textViewSunset.text = "Sunset Time: $sunsetTime"
        textViewDescription.text = "Description: $description"
        textViewWind.text = "Wind speed: $wind"
    }

    private fun convertTime(unixTimestamp: Long): String {
        val date = Date(unixTimestamp * 1000L)
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }


}