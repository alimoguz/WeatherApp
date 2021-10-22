package com.example.weatherapp.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.view.fragment.ApiKeyFragment
import com.example.weatherapp.view.fragment.WeatherDetailsFragment

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var toolBar: androidx.appcompat.widget.Toolbar
    private lateinit var toolbar_back_btn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        toolBar = findViewById(R.id.toolbar)
        toolbar_back_btn = findViewById(R.id.toolbar_back_btn)

        toolbar_back_btn.setOnClickListener {
            onBackPressed()
        }

        if (savedInstanceState == null) {
            toolBar.visibility = View.GONE
            openFragment(ApiKeyFragment.newInstance())
        }

        viewModel.weatherResponseList.observe(this, Observer {
            toolBar.visibility = View.VISIBLE
            openFragment(WeatherDetailsFragment.newInstance())
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        toolBar.visibility = View.GONE
    }

    private fun openFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (fragment is WeatherDetailsFragment) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction
            .replace(binding.container.id, fragment)
            .commit()
    }
}