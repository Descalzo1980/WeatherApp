package dev.stas.weatherapp.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "favorites_cities")
data class CityDBModel(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String
)
