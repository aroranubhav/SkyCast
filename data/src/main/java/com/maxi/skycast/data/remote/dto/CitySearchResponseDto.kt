package com.maxi.skycast.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CitySearchResponseDto(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)