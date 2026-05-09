package com.maxi.skycast.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cities",
    indices = [Index(
        value = ["latitude", "longitude"],
        unique = true
    )]
)
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val country: String,
    val state: String?,
    val latitude: Double,
    val longitude: Double,
    // Nullable because weather is fetched after insert
    val temperature: Double? = null,
    val feelsLike: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val weatherDescription: String? = null,
    val weatherIconCode: String? = null,
    val weatherTimestamp: Long? = null
)