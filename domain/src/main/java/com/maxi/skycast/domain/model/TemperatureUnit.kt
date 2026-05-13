package com.maxi.skycast.domain.model

import kotlin.math.roundToInt

enum class TemperatureUnit {
    CELSIUS,
    FAHRENHEIT;

    fun display(temp: Double): String = when (this) {
        CELSIUS -> {
            "${temp.roundToInt()}°C"
        }
        FAHRENHEIT -> {
            "${((temp * 9.0 / 5.0) + 32).roundToInt()}°F"
        }
    }
}