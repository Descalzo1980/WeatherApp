package dev.stas.weatherapp.domain.repository

import dev.stas.weatherapp.domain.entity.Forecast
import dev.stas.weatherapp.domain.entity.Weather

interface WeatherRepository {
    suspend fun getWeather(cityId: Int): Weather
    suspend fun getForecast(cityId: Int): Forecast
}