package com.maxi.skycast.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SysWeatherDto(
    val id: Long? = null,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
