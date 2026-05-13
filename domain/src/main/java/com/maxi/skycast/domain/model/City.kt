package com.maxi.skycast.domain.model

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val state: String?,
    val latitude: Double,
    val longitude: Double,
    val weather: Weather?   // null until first fetch
)
