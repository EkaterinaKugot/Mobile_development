package com.example.sensors

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAdapter: SensorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorAdapter = SensorAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = sensorAdapter

        val categories = listOf(
            "Датчики окружающей среды",
            "Датчики положения устройства",
            "Датчики состояния человека"
        )

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> showSensors()
                    1 -> showPositionSensors()
                    2 -> showMotionSensors()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })
    }

    private fun showSensors() {
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorAdapter.setSensors(sensors)
    }
    private fun showPositionSensors() {
        val sensors = listOf(
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        ).filterNotNull()
        sensorAdapter.setSensors(sensors)
    }

    private fun showMotionSensors() {
        val sensors = listOf(
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
            sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
        ).filterNotNull()
        sensorAdapter.setSensors(sensors)
    }

}