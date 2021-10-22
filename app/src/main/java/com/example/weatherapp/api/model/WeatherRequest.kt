package com.example.weatherapp.api.model

data class WeatherRequest(
    val lat: Double,
    val lon: Double,
    val exclude: String,
    val appId: String
)
