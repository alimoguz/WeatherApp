package com.example.weatherapp.view.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.api.WeatherResponseListener
import com.example.weatherapp.api.model.WeatherRequest
import com.example.weatherapp.api.response.Weather
import com.example.weatherapp.databinding.FragmentApiKeyBinding
import com.example.weatherapp.view.MainViewModel
import android.app.ProgressDialog




class ApiKeyFragment : Fragment() {

    companion object {
        private const val TAG = "ApiKeyFragment"
        fun newInstance() = ApiKeyFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentApiKeyBinding
    private var mContext: Context? = null
    private var locationManager: LocationManager? = null
    private lateinit var dialog: ProgressDialog
    val MY_PERMISSIONS_REQUEST_LOCATION = 999
    private lateinit var editText: EditText
    var apiKey: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiKeyBinding.inflate(layoutInflater)
        locationManager =
            mContext!!.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager?
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!

        editText = binding.apiKeyField
        val btnEnter = binding.enterBtn

        btnEnter.setOnClickListener {
            if (checkLocationPermission()) {
                dialog = ProgressDialog.show(
                    mContext, "Loading",
                    "Please wait...", true
                )
                try {
                    locationManager?.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0L,
                        0f,
                        locationListener
                    )
                } catch (ex: SecurityException) {
                    dialog.dismiss()
                    Log.d("myTag", "Security Exception, no location available")
                }
            }
        }
    }

    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                mContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mContext as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    mContext as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(
                    mContext as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true

        }

    }
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            apiKey = editText.text.toString()
            val weather = WeatherRequest(location.latitude, location.longitude, "current", apiKey)
            val listener: WeatherResponseListener = object : WeatherResponseListener {
                override fun onResponseError() {
                    dialog.dismiss()
                    Toast.makeText(mContext, "Hatalı Api Key lütfen tekrar deneyiniz.", Toast.LENGTH_LONG).show()
                    super.onResponseError()
                }

                override fun onResponseStart() {
                    dialog.show()
                    super.onResponseStart()
                }

                override fun onResponseFinish() {
                    dialog.dismiss()
                    super.onResponseFinish()
                }
            }
            viewModel.loadWeatherData(weather, listener)

        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

}