package dev.stas.weatherapp.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dev.stas.weatherapp.data.local.db.FavoriteDataBase
import dev.stas.weatherapp.data.local.db.FavoritesCitiesDao
import dev.stas.weatherapp.data.network.api.ApiFactory
import dev.stas.weatherapp.data.network.api.ApiService
import dev.stas.weatherapp.data.repository.FavoriteRepositoryImpl
import dev.stas.weatherapp.data.repository.SearchRepositoryImpl
import dev.stas.weatherapp.data.repository.WeatherRepositoryImpl
import dev.stas.weatherapp.domain.repository.FavoriteRepository
import dev.stas.weatherapp.domain.repository.SearchRepository
import dev.stas.weatherapp.domain.repository.WeatherRepository

@Module
interface DataModule {
    @[ApplicationScope Binds]
    fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
    @[ApplicationScope Binds]
    fun bindSearchRepository(impl: SearchRepositoryImpl): SearchRepository
    @[ApplicationScope Binds]
    fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    companion object {
        @[ApplicationScope Provides]
        fun provideApiService(): ApiService = ApiFactory.apiService
        @[ApplicationScope Provides]
        fun provideFavoriteDataBase(
            context: Context
        ): FavoriteDataBase {
            return FavoriteDataBase.getInstance(context)
        }
        @[ApplicationScope Provides]
        fun provideFavoriteCitiesDao(dataBase: FavoriteDataBase): FavoritesCitiesDao{
            return dataBase.favoritesCitiesDao()
        }
    }
}