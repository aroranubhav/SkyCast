package com.maxi.skycast.domain.repository

import com.maxi.skycast.domain.model.City
import com.maxi.skycast.domain.model.CitySearchResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getSavedCities(): Flow<List<City>>

    suspend fun searchCities(query: String): Result<List<CitySearchResult>>

    suspend fun addCity(city: CitySearchResult): Result<Unit>

    suspend fun deleteCity(cityId: Int)

    suspend fun refreshWeatherForCity(cityId: Int): Result<Unit>

    suspend fun refreshWeatherForAllCities(): Result<Unit>
}