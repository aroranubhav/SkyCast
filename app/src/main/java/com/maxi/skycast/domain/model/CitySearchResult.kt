package com.maxi.skycast.domain.model

data class CitySearchResult(
    val name: String,
    val country: String,
    val state: String?,
    val latitude: Double,
    val longitude: Double
)
