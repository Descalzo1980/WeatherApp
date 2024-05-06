package dev.stas.weatherapp.presentation.details

import kotlinx.coroutines.flow.StateFlow

interface DetailComponent {

    val model: StateFlow<DetailStore.State>

    fun onClickBack()

    fun onClickChangeFavoriteStatus()
}