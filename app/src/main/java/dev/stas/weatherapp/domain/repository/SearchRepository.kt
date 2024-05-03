package dev.stas.weatherapp.domain.repository

import dev.stas.weatherapp.domain.entity.City

interface SearchRepository {
    suspend fun search(query: String): List<City>
}