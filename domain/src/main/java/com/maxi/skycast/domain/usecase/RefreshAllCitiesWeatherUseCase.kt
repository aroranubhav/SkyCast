package com.maxi.skycast.domain.usecase

import com.maxi.skycast.domain.repository.WeatherRepository
import javax.inject.Inject

class RefreshAllCitiesWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(): Result<Unit> =
        repository.refreshWeatherForAllCities()
}