package dev.stas.weatherapp.presentation.favorite

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.presentation.extensions.componentScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DefaultFavoriteComponent @Inject constructor(
    private val favoriteStoreFactory: FavoriteStoreFactory,
    private val onCityItemClicked: (City) -> Unit,
    private val onAddFavoriteClick: () -> Unit,
    private val onSearchClicked: () -> Unit,
    componentContext: ComponentContext
) : FavoriteComponent, ComponentContext by componentContext{

    private val store = instanceKeeper.getStore { favoriteStoreFactory.create() }
    private val scope = componentScope()
    init {
        scope.launch {
            store.labels.collect{
                when(it){
                    is FavoriteStore.Label.CityItemClicked -> {
                        onCityItemClicked(it.city)
                    }
                    FavoriteStore.Label.ClickSearch -> {
                        onSearchClicked()
                    }
                    FavoriteStore.Label.ClickAddToFavorite -> {
                        onAddFavoriteClick()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val model: StateFlow<FavoriteStore.State> = store.stateFlow

    override fun onClickSearch() {
        store.accept(FavoriteStore.Intent.ClickSearch)
    }

    override fun onClickAddFavorites() {
        store.accept(FavoriteStore.Intent.ClickAddToFavorite)
    }

    override fun onCityItemClick(city: City) {
        store.accept(FavoriteStore.Intent.CityItemClicked(city))
    }
}