package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.api.response.Weather
import com.example.weatherapp.databinding.RowWeatherBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(private val itemList: List<Weather>) :
    RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            RowWeatherBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = itemList.get(position)

        holder.rowWeatherBinding.day.text = weather.dt?.let { getDateTime(it) }
        Picasso.get()
            .load("http://openweathermap.org/img/wn/${weather.icon}@2x.png")
            .into(holder.rowWeatherBinding.weatherIcon)
        holder.rowWeatherBinding.daylightDegree.text = weather.tempDay?.let { kelvinToCelsius(it)}
        holder.rowWeatherBinding.nightDegree.text = weather.tempNight?.let { kelvinToCelsius(it) }
    }

    override fun getItemCount() = itemList.size

    private fun kelvinToCelsius(kelvin: Float): String {
        val celsius = kelvin - 273.15f
        return String.format("%.0f\u2103", celsius)
    }

    private fun getDateTime(l: Long): String? {
        try {
            val sdf = SimpleDateFormat("EEEE")
            val netDate = Date(l * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}