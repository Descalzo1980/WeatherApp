package dev.stas.weatherapp.presentation.details

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.domain.entity.Forecast
import dev.stas.weatherapp.domain.usecase.ChangeFavoriteStateUseCase
import dev.stas.weatherapp.domain.usecase.GetForecastUseCase
import dev.stas.weatherapp.domain.usecase.ObserveFavoriteStateUseCase
import dev.stas.weatherapp.presentation.details.DetailStore.Intent
import dev.stas.weatherapp.presentation.details.DetailStore.Label
import dev.stas.weatherapp.presentation.details.DetailStore.State
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailStore : Store<Intent, State, Label> {

    sealed interface Intent {

        data object ClickBack: Intent

        data object ClickChangeFavoriteStatus: Intent

    }

    data class State(
        val city: City,
        val isFavorite: Boolean,
        val forecastState: ForecastState
    ){

        sealed interface ForecastState{
            data object InitialState: ForecastState

            data object Loading: ForecastState

            data object Error: ForecastState

            data class Loaded(
                val forecast: Forecast
            ): ForecastState
        }
    }

    sealed interface Label {

        data object ClickBack: Label
    }
}

class DetailStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val getForecastUseCase: GetForecastUseCase,
    private val changeFavoriteStateUseCase: ChangeFavoriteStateUseCase,
    private val observeFavoriteStateUseCase: ObserveFavoriteStateUseCase
) {

    fun create(city: City): DetailStore =
        object : DetailStore, Store<Intent, State, Label> by storeFactory.create(
            name = "DetailStore",
            initialState = State(
                city = city,
                isFavorite = false,
                forecastState = State.ForecastState.InitialState
            ),
            bootstrapper = BootstrapperImpl(city),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {

        data class FavoriteStatusChange(val isFavorite: Boolean): Action
        data class ForecastLoaded(val forecast: Forecast): Action
        data object ForecastStartLoading: Action
        data object ForecastLoadingError: Action
    }

    private sealed interface Msg {

        data class FavoriteStatusChange(val isFavorite: Boolean): Msg
        data class ForecastLoaded(val forecast: Forecast): Msg
        data object ForecastStartLoading: Msg
        data object ForecastLoadingError: Msg
    }

    private inner class BootstrapperImpl(
        private val city: City
    ) : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {
                observeFavoriteStateUseCase(cityId = city.id).collect {
                    dispatch(Action.FavoriteStatusChange(it))
                }
            }
            scope.launch {
                dispatch(Action.ForecastStartLoading)
                try {
                    val forecast = getForecastUseCase(cityId = city.id)
                    dispatch(Action.ForecastLoaded(forecast = forecast))
                } catch (e: Exception) {
                    dispatch(Action.ForecastLoadingError)
                }
            }
        }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when(intent){
                Intent.ClickBack -> {
                    publish(Label.ClickBack)
                }
                Intent.ClickChangeFavoriteStatus -> {
                    scope.launch {
                        val state = getState()
                        if(state.isFavorite){
                            changeFavoriteStateUseCase.removeFromFavorite(state.city.id)
                        }else{
                            changeFavoriteStateUseCase.addToFavorite(state.city)
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when(action){
                is Action.FavoriteStatusChange -> {
                    dispatch(Msg.FavoriteStatusChange(getState().isFavorite))
                }
                is Action.ForecastLoaded -> {
                    dispatch(Msg.ForecastLoaded(action.forecast))
                }
                Action.ForecastLoadingError -> {
                    dispatch(Msg.ForecastLoadingError)
                }
                Action.ForecastStartLoading -> {
                    dispatch(Msg.ForecastStartLoading)
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State = when(msg){
            is Msg.FavoriteStatusChange -> {
                copy(isFavorite = msg.isFavorite)
            }
            is Msg.ForecastLoaded -> {
                copy(forecastState = State.ForecastState.Loaded(msg.forecast))
            }
            Msg.ForecastLoadingError -> {
                copy(forecastState = State.ForecastState.Error)
            }
            Msg.ForecastStartLoading -> {
                copy(forecastState = State.ForecastState.Loading)

            }
        }
    }
}
