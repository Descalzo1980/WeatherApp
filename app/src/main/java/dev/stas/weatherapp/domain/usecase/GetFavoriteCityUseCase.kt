package dev.stas.weatherapp.domain.usecase

import dev.stas.weatherapp.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteCityUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {
    operator fun invoke() = repository.favoriteCities
}