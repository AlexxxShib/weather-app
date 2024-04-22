package com.alexxxshib.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alexxxshib.weatherapp.data.network.api.ApiFactory
import com.alexxxshib.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            Log.d("MainActivity", apiService.loadCurrentWeather("London").toString())
            Log.d("MainActivity", apiService.loadForecast("London").toString())
            Log.d("MainActivity", apiService.searchCity("London").joinToString { it.toString() })
        }

        setContent {
            WeatherAppTheme {

            }
        }
    }
}
