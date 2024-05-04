package dev.stas.weatherapp.data.repository

import dev.stas.weatherapp.data.mapper.toEntity
import dev.stas.weatherapp.data.network.api.ApiService
import dev.stas.weatherapp.domain.entity.Forecast
import dev.stas.weatherapp.domain.entity.Weather
import dev.stas.weatherapp.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WeatherRepository {
    override suspend fun getWeather(cityId: Int): Weather {
        return apiService.loadCurrentWeather("$PREFIX_CITY_ID$cityId").toEntity()
    }

    override suspend fun getForecast(cityId: Int): Forecast {
        return apiService.loadForecast("$PREFIX_CITY_ID$cityId").toEntity()
    }

    private companion object {
        private const val PREFIX_CITY_ID = "id:"
    }
}