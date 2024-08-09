package com.obedcodes.openweatherreq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val weatherList: List<WeatherItem>) :
    RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val tempTextView: TextView = view.findViewById(R.id.tempTextView)
        val descTextView: TextView = view.findViewById(R.id.descTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weatherItem = weatherList[position]
        holder.dateTextView.text = weatherItem.date
        holder.tempTextView.text = "${weatherItem.temp}°C"
        holder.descTextView.text = weatherItem.description
    }

    override fun getItemCount() = weatherList.size
}
