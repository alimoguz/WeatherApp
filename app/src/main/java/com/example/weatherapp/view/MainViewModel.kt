package com.example.weatherapp.view

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.ApiInterface
import com.example.weatherapp.api.Client
import com.example.weatherapp.api.WeatherResponseListener
import com.example.weatherapp.api.model.WeatherRequest
import com.example.weatherapp.api.response.Daily
import com.example.weatherapp.api.response.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainViewModel : ViewModel() {

    private val TAG = "MainViewModel"

    private val _timeZone = MutableLiveData<String>()
    private val _weatherResponseList = MutableLiveData<List<Daily>>()

    val timeZone = _timeZone
    val weatherResponseList = _weatherResponseList


    fun loadWeatherData(weatherRequest: WeatherRequest, responseListener: WeatherResponseListener) {

        responseListener.onResponseStart()

        Client.buildService(ApiInterface::class.java).getWeatherData(
            weatherRequest.lat,
            weatherRequest.lon,
            weatherRequest.exclude,
            weatherRequest.appId
        ).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                responseListener.onResponseFinish()

                if (response.isSuccessful) {
                    response.body()?.let {
                        _timeZone.postValue(it.timeZone)
                        _weatherResponseList.postValue(it.daily)
                    }
                }
                else{
                    responseListener.onResponseError()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                responseListener.onResponseFinish()
            }

        })
    }
}