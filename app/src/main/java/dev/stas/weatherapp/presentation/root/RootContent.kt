package dev.stas.weatherapp.presentation.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import dev.stas.weatherapp.presentation.details.DetailsContent
import dev.stas.weatherapp.presentation.favorite.FavoriteComponent
import dev.stas.weatherapp.presentation.favorite.FavoriteContent
import dev.stas.weatherapp.presentation.search.SearchContent
import dev.stas.weatherapp.presentation.ui.theme.WeatherAppTheme

@Composable
fun RootContent(component: RootComponent) {
    WeatherAppTheme {
        Children(stack = component.stack) {
            when(val instance = it.instance){
                is RootComponent.Child.Details -> {
                    DetailsContent(component = instance.component)
                }
                is RootComponent.Child.Favorite -> {
                    FavoriteContent(component = instance.component)
                }
                is RootComponent.Child.Search -> {
                    SearchContent(component = instance.component)
                }
            }
        }
    }
}