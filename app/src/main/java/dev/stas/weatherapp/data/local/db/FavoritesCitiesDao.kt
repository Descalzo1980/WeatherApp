package dev.stas.weatherapp.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.stas.weatherapp.data.local.model.CityDBModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCitiesDao {

    @Query("SELECT * FROM favorites_cities")
    fun getFavouriteCities(): Flow<List<CityDBModel>>

    @Query("SELECT EXISTS (SELECT * FROM favorites_cities WHERE id=:cityId LIMIT 1)")
    fun observeIsFavourite(cityId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourite(cityDbModel: CityDBModel)

    @Query("DELETE FROM favorites_cities WHERE id=:cityId")
    suspend fun removeFromFavourite(cityId: Int)
}