package dev.stas.weatherapp.data.repository

import dev.stas.weatherapp.data.mapper.toEntities
import dev.stas.weatherapp.data.network.api.ApiService
import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : SearchRepository {
    override suspend fun search(query: String): List<City> {
        return apiService.searchCity(query).toEntities()
    }
}