package dev.stas.weatherapp.data.mapper

import dev.stas.weatherapp.data.network.dto.CityDto
import dev.stas.weatherapp.domain.entity.City

fun CityDto.toEntity(): City = City(
    id, country, name
)

fun List<CityDto>.toEntities(): List<City> = map {
    it.toEntity()
}