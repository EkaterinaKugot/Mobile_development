package com.example.weather2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Scanner

class BriefWeatherFragment : Fragment(){

    data class WeatherData(
        @SerializedName("weather") val weather: List<Weather>,
    )

    data class Weather(
        @SerializedName("description") val description: String,
        @SerializedName("icon") val icon: String
    )


    private lateinit var imageWeather: ImageView
    private lateinit var textViewDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_brief_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageWeather = view.findViewById<ImageView>(R.id.imageWeather)
        textViewDescription = view.findViewById<TextView>(R.id.textViewDescription)
        GlobalScope.launch(Dispatchers.IO) {
            loadWeather()
        }

    }

    private suspend fun loadWeather() {
        val API_KEY = getString(R.string.api_key)

        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=London&appid=$API_KEY&units=metric"
        val stream = URL(weatherURL).openStream()

        val data = Scanner(stream).useDelimiter("\\A").next()
        val weatherData = Gson().fromJson(data, WeatherData::class.java)

        withContext(Dispatchers.Main) {
            updateUI(weatherData)
        }
    }

    private fun updateUI(weatherData: WeatherData) {
        val description = weatherData.weather[0].description
        val icon = weatherData.weather[0].icon

        imageWeather.setImageResource(getImage(icon))
        textViewDescription.text = description
    }


    private fun getImage(icon: String): Int {
        return when (icon) {
            "01d" -> R.drawable.d01
            "01n" -> R.drawable.n01
            "02d"-> R.drawable.d02
            "02n"-> R.drawable.n02
            "03d",
            "03n" -> R.drawable.d03
            "04d",
            "04n" -> R.drawable.d04
            "09d",
            "09n" -> R.drawable.d09
            "10d" -> R.drawable.d10
            "10n" -> R.drawable.n10
            "11d",
            "11n"-> R.drawable.d11
            "13d",
            "13n" -> R.drawable.d13
            "50d",
            "50n" -> R.drawable.d50

            else -> R.drawable.d03
        }
    }
}