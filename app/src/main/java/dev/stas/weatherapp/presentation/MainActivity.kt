package dev.stas.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.stas.weatherapp.data.network.api.ApiFactory
import dev.stas.weatherapp.presentation.ui.theme.WeatherAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.IO).launch {
           val currentWeather =  apiService.loadCurrentWeather("Novi Sad")
           val forecastWeather =  apiService.loadForecast("Novi Sad")
           val cities =  apiService.searchCity("Novi Sad")
            Log.d("MYTAGMATAG","$currentWeather")
            Log.d("MYTAGMATAG", "$forecastWeather")
            Log.d("MYTAGMATAG", "$cities")
        }
        setContent {
            WeatherAppTheme {

            }
        }
    }
}