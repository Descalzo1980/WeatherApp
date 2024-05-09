package dev.stas.weatherapp.data.mapper

import dev.stas.weatherapp.data.local.model.CityDBModel
import dev.stas.weatherapp.domain.entity.City

fun City.toDBModel(): CityDBModel = CityDBModel(id, name, country)

fun CityDBModel.toEntity(): City = City(id, name, country)

fun List<CityDBModel>.toEntities(): List<City> = map { it.toEntity() }