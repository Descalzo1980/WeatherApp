package dev.stas.weatherapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CityDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
)