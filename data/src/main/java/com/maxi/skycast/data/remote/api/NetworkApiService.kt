package com.maxi.skycast.data.remote.api

import com.maxi.skycast.data.remote.dto.CitySearchResponseDto
import com.maxi.skycast.data.remote.dto.WeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApiService {

    @GET("geo/1.0/direct")
    suspend fun searchCities(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5
    ): List<CitySearchResponseDto>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): WeatherResponseDto
}