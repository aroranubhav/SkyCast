package com.maxi.skycast.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponseDto(
    val name: String,
    val weather: List<WeatherDescriptionDto>,
    val main: MainWeatherDto,
    val sys: SysWeatherDto,
    val wind: WindDto,
    val visibility: Int,
    @SerialName("dt")
    val dataCalculationTime: Long
)
