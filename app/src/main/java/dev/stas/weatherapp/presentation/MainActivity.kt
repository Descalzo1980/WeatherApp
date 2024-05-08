package dev.stas.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import dev.stas.weatherapp.WeatherApp
import dev.stas.weatherapp.domain.usecase.ChangeFavoriteStateUseCase
import dev.stas.weatherapp.domain.usecase.SearchCityUseCase
import dev.stas.weatherapp.presentation.root.DefaultRootComponent
import dev.stas.weatherapp.presentation.root.RootContent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

    @Inject
    lateinit var searchCityUseCase: SearchCityUseCase

    @Inject
    lateinit var changeFavoriteStateUseCase: ChangeFavoriteStateUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as WeatherApp).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

        val scope = CoroutineScope(Dispatchers.Main)

        scope.launch {
            searchCityUseCase("пон").forEach {
                changeFavoriteStateUseCase.addToFavorite(city = it)
            }
        }
        setContent {
            RootContent(component = rootComponentFactory.create(defaultComponentContext()))
        }
    }
}