package dev.stas.weatherapp.presentation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.stas.weatherapp.presentation.details.DetailComponent
import dev.stas.weatherapp.presentation.favorite.FavoriteComponent
import dev.stas.weatherapp.presentation.search.SearchComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed interface Child{
        data class Favorite(val component: FavoriteComponent): Child
        data class Search(val component: SearchComponent): Child
        data class Details(val component: DetailComponent): Child
    }
}