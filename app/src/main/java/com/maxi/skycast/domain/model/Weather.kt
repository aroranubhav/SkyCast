package com.maxi.skycast.domain.model

data class Weather(
    val temperature: Double,
    val feelsLike: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val iconCode: String,
    val timestamp: Long
)
