package dev.stas.weatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.presentation.details.DefaultDetailComponent
import dev.stas.weatherapp.presentation.favorite.DefaultFavoriteComponent
import dev.stas.weatherapp.presentation.search.DefaultSearchComponent
import dev.stas.weatherapp.presentation.search.OpenReason
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailComponent.Factory,
    private val favoriteComponentFactory: DefaultFavoriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext{
    override val stack: Value<ChildStack<*, RootComponent.Child>>
        get() = TODO("Not yet implemented")

    private fun config(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child{
        return when(config){
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onBackClick = {

                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }
            Config.Favorite -> {
                val component = favoriteComponentFactory.create(
                    onCityItemClicked = {

                    },
                    onAddFavoriteClick = {

                    },
                    onSearchClicked = {

                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Favorite(component)
            }
            is Config.Search -> {
                val component = searchComponentFactory.create(
                    openReason = config.openReason,
                    onBackClick = {

                    },
                    onCitySavedFavorite = {

                    },
                    onForecastForCityRequested = {

                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }
        }
    }

    sealed interface Config: Parcelable {
        @Parcelize
        data object Favorite: Config

        @Parcelize
        data class Search(val openReason: OpenReason): Config

        @Parcelize
        data class Details(val city: City): Config

    }
    @AssistedFactory
    interface Factory{
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}