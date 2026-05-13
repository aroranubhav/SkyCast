package com.maxi.skycast.data.repository

import com.maxi.skycast.data.local.dao.CityDao
import com.maxi.skycast.data.local.entity.CityEntity
import com.maxi.skycast.data.mapper.toDomain
import com.maxi.skycast.data.mapper.toEntity
import com.maxi.skycast.data.mapper.toWeather
import com.maxi.skycast.data.mapper.withWeather
import com.maxi.skycast.data.remote.api.NetworkApiService
import com.maxi.skycast.domain.model.City
import com.maxi.skycast.domain.model.CitySearchResult
import com.maxi.skycast.domain.model.Weather
import com.maxi.skycast.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultWeatherRepository @Inject constructor(
    private val apiService: NetworkApiService,
    private val cityDao: CityDao
) : WeatherRepository {

    override fun getSavedCities(): Flow<List<City>> =
        cityDao.getAllCities().map {
            it.map(
                CityEntity::toDomain
            )
        }

    override suspend fun searchCities(query: String): Result<List<CitySearchResult>> =
        runCatching {
            apiService
                .searchCities(query).map {
                    it.toDomain()
                }
        }

    override suspend fun addCity(city: CitySearchResult): Result<Unit> =
        runCatching {
            val rowId = cityDao.addCity(city.toEntity())

            if (rowId == -1L) {
                return@runCatching //duplicate, skip silently
            }

            val entity = cityDao.getCityById(rowId.toInt()) ?: return@runCatching
            val weather = getCurrentWeather(entity)
            cityDao.updateCity(entity.withWeather(weather))
        }

    override suspend fun refreshWeatherForCity(cityId: Int): Result<Unit> =
        runCatching {
            val entity = cityDao.getCityById(cityId) ?: return@runCatching
            val weather = getCurrentWeather(entity)
            cityDao.updateCity(entity.withWeather(weather))
        }

    override suspend fun refreshWeatherForAllCities(): Result<Unit> = runCatching {
        cityDao
            .getAllCities()
            .first() // snapshot(first is a terminal operator -- suspends until one emission arrives, then stops collecting) -- no need to observe here
            .forEach { entity ->
                val weather = getCurrentWeather(entity)
                cityDao.updateCity(entity.withWeather(weather))
            }
    }

    override suspend fun deleteCity(cityId: Int) {
        cityDao.deleteCity(cityId)
    }

    private suspend fun getCurrentWeather(cityEntity: CityEntity): Weather {
        return apiService.getCurrentWeather(
            cityEntity.latitude,
            cityEntity.longitude
        ).toWeather()
    }
}