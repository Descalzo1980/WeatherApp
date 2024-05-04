package dev.stas.weatherapp.data.mapper

import dev.stas.weatherapp.data.local.model.CityDBModel
import dev.stas.weatherapp.domain.entity.City

fun City.toDBModel(): CityDBModel = CityDBModel(
    id = id,
    name = name,
    country = country
)

fun CityDBModel.toEntity() : City = City(
    id = id,
    name = name,
    country = country
)

fun List<CityDBModel>.toEntities(): List<City> =
    map { it.toEntity() }