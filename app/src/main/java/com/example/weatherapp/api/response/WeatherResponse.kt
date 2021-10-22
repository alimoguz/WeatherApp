package com.example.weatherapp.api.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val lat: Float?,
    val lon: Float?,
    @SerializedName("timezone")
    val timeZone: String?,
    @SerializedName("timezone_offset")
    val timeZoneOffset: Int?,
    val daily: List<Daily>
)