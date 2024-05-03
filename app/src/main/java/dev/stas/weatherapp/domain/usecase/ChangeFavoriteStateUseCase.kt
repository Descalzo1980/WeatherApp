package dev.stas.weatherapp.domain.usecase

import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class ChangeFavoriteStateUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    suspend fun addToFavorite(city: City) = repository.addToFavorite(city)
    suspend fun removeFromFavorite(cityId: Int) = repository.removeFromFavorite(cityId)
}