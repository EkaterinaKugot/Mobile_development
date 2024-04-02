package com.example.gps_practice

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(), LocationListener {
    val LOCATION_PERM_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // запрашиваем разрешения на доступ к геопозиции
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            // переход в запрос разрешений
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERM_CODE
            )
        } else {
            startLocationUpdates()
        }
    }

    override fun onLocationChanged(loc: Location) {
        val lat = loc.latitude
        val lng = loc.longitude
        displayCoord(lat, lng)
        Log.d("my", "lat " + lat + " long " + lng)
    }

    fun displayCoord(latitude: Double, longtitude: Double) {
        findViewById<TextView>(R.id.lat).text = String.format("%.5f", latitude)
        findViewById<TextView>(R.id.lng).text = String.format("%.5f", longtitude)
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
        Toast.makeText(
            this,
            "Provider $provider enabled",
            Toast.LENGTH_LONG
        ).show()
        Log.d("mytag", "Provider $provider enabled")
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
        Toast.makeText(
            this,
            "Provider $provider disabled",
            Toast.LENGTH_LONG
        ).show()
        Log.d("mytag", "Provider $provider disabled")
    }

    override fun onResume() {
        super.onResume()
        // Проверяем, доступны ли провайдеры геолокации
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = locationManager.getProviders(true)
        val strproviders = providers.toString()
        Toast.makeText(
            this,
            "Available providers: $providers",
            Toast.LENGTH_LONG
        ).show()
        Log.d("mytag", "Available providers: $strproviders")
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

            val prv = locationManager.getBestProvider(Criteria(), true)
            if (prv != null) {
                val location = locationManager.getLastKnownLocation(prv)
                findViewById<Button>(R.id.updButton).setOnClickListener {
                    if (location != null) {
                        displayCoord(location.latitude, location.longitude)
                    }
                    Log.d("mytag", "location set")
                }
            }
        }
    }

    private fun stopLocationUpdates() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationManager.removeUpdates(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERM_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Toast.makeText(
                    this,
                    "Для корректной работы приложения необходимо разрешить доступ к геолокации",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}