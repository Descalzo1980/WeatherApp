package dev.stas.weatherapp.presentation.favorite

import dev.stas.weatherapp.domain.entity.City
import kotlinx.coroutines.flow.StateFlow

interface FavoriteComponent {

    val model: StateFlow<FavoriteStore.State>

    fun onClickSearch()

    fun onClickAddFavorites()

    fun onCityItemClick(city: City)
}