package com.example.weatherapp.api.response

data class Weather(
    val dt: Long?,
    val tempDay: Float?,
    val tempNight: Float?,
    val icon: String?
)
