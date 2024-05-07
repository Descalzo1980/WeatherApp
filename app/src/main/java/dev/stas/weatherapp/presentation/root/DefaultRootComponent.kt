package dev.stas.weatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
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

class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailComponent.Factory,
    private val favoriteComponentFactory: DefaultFavoriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favorite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.Details -> {
                val component = detailsComponentFactory.create(
                    city = config.city,
                    onBackClick = {
                        navigation.pop()
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }

            Config.Favorite -> {
                val component = favoriteComponentFactory.create(
                    onCityItemClicked = {
                        navigation.push(Config.Details(it))
                    },
                    onAddFavoriteClick = {
                        navigation.push(Config.Search(OpenReason.AddToFavorite))
                    },
                    onSearchClicked = {
                        navigation.push(Config.Search(OpenReason.RegularSearch))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Favorite(component)
            }

            is Config.Search -> {
                val component = searchComponentFactory.create(
                    openReason = config.openReason,
                    onBackClick = {
                        navigation.pop()
                    },
                    onCitySavedToFavorite = {
                        navigation.pop()
                    },
                    onForecastForCityRequested = {
                        navigation.push(Config.Details(it))
                    },
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }
        }
    }

    sealed interface Config : Parcelable {
        @Parcelize
        data object Favorite : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config

        @Parcelize
        data class Details(val city: City) : Config

    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}