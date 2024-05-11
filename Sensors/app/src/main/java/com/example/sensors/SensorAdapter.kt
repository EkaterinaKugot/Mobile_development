package com.example.sensors

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SensorAdapter : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>(){
    private var sensors: List<Sensor> = emptyList()

    inner class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sensorName: TextView = itemView.findViewById(R.id.sensor_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.sensor_item, parent, false)
        return SensorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val currentSensor = sensors[position]
        holder.sensorName.text = currentSensor.name
    }

    override fun getItemCount(): Int {
        return sensors.size
    }

    fun setSensors(sensors: List<Sensor>) {
        this.sensors = sensors
        notifyDataSetChanged()
    }
}