package dev.stas.weatherapp.data.repository

import dev.stas.weatherapp.data.local.db.FavoritesCitiesDao
import dev.stas.weatherapp.data.mapper.toDBModel
import dev.stas.weatherapp.data.mapper.toEntities
import dev.stas.weatherapp.domain.entity.City
import dev.stas.weatherapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteCitiesDao: FavoritesCitiesDao
): FavoriteRepository {

    override val favoriteCities: Flow<List<City>> =
        favoriteCitiesDao.getFavoritesCities()
            .map { it.toEntities() }

    override fun observeIsFavorite(cityId: Int): Flow<Boolean> =
        favoriteCitiesDao.observeIsFavorites(cityId)

    override suspend fun addToFavorite(city: City) =
        favoriteCitiesDao.addToFavorite(city.toDBModel())

    override suspend fun removeFromFavorite(cityId: Int) =
        favoriteCitiesDao.removeFromFavorite(cityId)
}