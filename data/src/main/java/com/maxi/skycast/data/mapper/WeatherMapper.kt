package com.maxi.skycast.data.mapper

import com.maxi.skycast.data.local.entity.CityEntity
import com.maxi.skycast.data.remote.dto.CitySearchResponseDto
import com.maxi.skycast.data.remote.dto.WeatherResponseDto
import com.maxi.skycast.domain.model.City
import com.maxi.skycast.domain.model.CitySearchResult
import com.maxi.skycast.domain.model.Weather

fun WeatherResponseDto.toWeather(): Weather =
    Weather(
        main.temp,
        main.feelsLike,
        main.minTemp,
        main.maxTemp,
        main.humidity,
        wind.speed,
        weather.firstOrNull()?.description.orEmpty(),
        weather.firstOrNull()?.icon.orEmpty(),
        dataCalculationTime
    )

fun CitySearchResponseDto.toDomain(): CitySearchResult =
    CitySearchResult(
        name,
        country,
        state,
        lat,
        lon
    )

fun CitySearchResult.toEntity(): CityEntity =
    CityEntity(
        0,
        name,
        country,
        state,
        latitude,
        longitude
    )

fun CityEntity.toDomain(): City =
    City(
        id,
        name,
        country,
        state,
        latitude,
        longitude,
        weather = if (temperature != null) {
            Weather(
                temperature = temperature,
                feelsLike = feelsLike!!,
                minTemp = tempMin!!,
                maxTemp = tempMax!!,
                humidity = humidity!!,
                windSpeed = windSpeed!!,
                description = weatherDescription!!,
                iconCode = weatherIconCode!!,
                timestamp = weatherTimestamp!!
            )
        } else null
    )

// Utility to apply fresh weather onto an existing entity
fun CityEntity.withWeather(weather: Weather): CityEntity = copy(
    temperature = weather.temperature,
    feelsLike = weather.feelsLike,
    tempMin = weather.minTemp,
    tempMax = weather.maxTemp,
    humidity = weather.humidity,
    windSpeed = weather.windSpeed,
    weatherDescription = weather.description,
    weatherIconCode = weather.iconCode,
    weatherTimestamp = weather.timestamp
)