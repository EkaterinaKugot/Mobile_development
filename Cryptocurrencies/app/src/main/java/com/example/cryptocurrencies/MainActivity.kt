package com.example.cryptocurrencies

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btcUsdTextView: TextView
    private lateinit var btcEurTextView: TextView
    private lateinit var etcUsdTextView: TextView
    private lateinit var etcEurTextView: TextView
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btcUsdTextView = findViewById(R.id.btcUsdTextView)
        btcEurTextView = findViewById(R.id.btcEurTextView)
        etcUsdTextView = findViewById(R.id.etcUsdTextView)
        etcEurTextView = findViewById(R.id.etcEurTextView)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            updateCrypto()
        }
    }

    private fun updateCrypto() {
        GlobalScope.launch(Dispatchers.IO) {
            val url =
                "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH&tsyms=USD,EUR"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    val responseData = response.body?.string()
                    val json = JSONObject(responseData)
                    val btcUsd = json.getJSONObject("BTC").getString("USD")
                    val btcEur = json.getJSONObject("BTC").getString("EUR")
                    val ethUsd = json.getJSONObject("ETH").getString("USD")
                    val ethEur = json.getJSONObject("ETH").getString("EUR")

                    withContext(Dispatchers.Main) {
                        btcUsdTextView.text = "BTC/USD: $btcUsd"
                        btcEurTextView.text = "BTC/EUR: $btcEur"
                        etcUsdTextView.text = "ETH/USD: $ethUsd"
                        etcEurTextView.text = "ETH/EUR: $ethEur"
                    }
                }
            } catch (e: IOException) {
                Toast.makeText(
                    applicationContext,
                    "Не удалось получить данные!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}