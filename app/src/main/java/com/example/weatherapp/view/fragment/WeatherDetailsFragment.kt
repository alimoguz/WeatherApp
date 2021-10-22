package com.example.weatherapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.adapters.WeatherAdapter
import com.example.weatherapp.api.response.Daily
import com.example.weatherapp.api.response.Weather
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.view.MainViewModel
import com.squareup.picasso.Picasso

class WeatherDetailsFragment : Fragment() {

    companion object {
        private const val TAG = "WeatherDetailsFragment"
        fun newInstance() = WeatherDetailsFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentWeatherDetailsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!

        val dailyList = arrayListOf<Daily>()
        viewModel.weatherResponseList.value?.let { dailyList.addAll(it) }

        val today = dailyList[0]
        dailyList.removeAt(0)

        // timeZone
        val cityName = viewModel.timeZone.value
        binding.city.text = cityName

        // icon
        val iconToday = today.weather?.get(0)?.icon
        Picasso.get()
            .load("http://openweathermap.org/img/wn/${iconToday}@2x.png")
            .into(binding.weather)

        // temp
        val tempDay = today.temp?.day
        binding.dayTemp.text = tempDay?.let { kelvinToCelsius(it) }

        val weatherList = arrayListOf<Weather>()

        for (daily in dailyList!!) {
            val dt = daily.dt
            val tempDay = daily.temp?.day
            val tempNight = daily.temp?.night
            val icon = daily.weather?.get(0)?.icon

            val weather = Weather(dt, tempDay, tempNight, icon)
            weatherList.add(weather)
        }


        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = WeatherAdapter(weatherList)
        }

    }

    private fun kelvinToCelsius(kelvin: Float): String {
        val celsius = kelvin - 273.15f
        return String.format("%.0f\u2103", celsius)
    }

}