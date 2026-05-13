package com.maxi.skycast.domain.usecase

import com.maxi.skycast.domain.model.CitySearchResult
import com.maxi.skycast.domain.repository.WeatherRepository
import javax.inject.Inject

class AddCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(city: CitySearchResult) =
        repository.addCity(city)
}