package com.example.weatherapp.api.response

import com.google.gson.annotations.SerializedName

data class Daily(
    val dt: Long?,
    @SerializedName("sunRise")
    val sunRise: Int?,
    @SerializedName("sunset")
    val sunSet: Int?,
    @SerializedName("moonrise")
    val moonRise: Int?,
    @SerializedName("moonset")
    val moonSet: Int?,
    @SerializedName("moon_phase")
    val moonPhrase: Float?,
    @SerializedName("temp")
    val temp: Temp?,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike?,
    val pressure: Int?,
    val humidity: Int?,
    @SerializedName("dew_point")
    val dewPoint: Float?,
    @SerializedName("wind_speed")
    val windSpeed: Float?,
    @SerializedName("wind_deg")
    val windDeg: Float?,
    @SerializedName("wind_gust")
    val windGust: Float?,
    val weather: List<Weather>?,
    val clouds: Int?,
    val pop: Float?,
    val rain: Float?,
    val uvi: Float?







)
