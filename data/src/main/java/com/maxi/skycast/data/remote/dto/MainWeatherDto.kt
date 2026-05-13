package com.maxi.skycast.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainWeatherDto(
    val temp: Double,
    @SerialName("temp_min")
    val minTemp: Double,
    @SerialName("temp_max")
    val maxTemp: Double,
    val humidity: Int,
    @SerialName("feels_like")
    val feelsLike: Double
)
