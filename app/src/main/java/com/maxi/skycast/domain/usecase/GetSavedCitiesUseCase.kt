package com.maxi.skycast.domain.usecase

import com.maxi.skycast.domain.model.City
import com.maxi.skycast.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    operator fun invoke(): Flow<List<City>> =
        repository.getSavedCities()
}