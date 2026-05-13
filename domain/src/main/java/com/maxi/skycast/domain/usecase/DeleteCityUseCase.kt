package com.maxi.skycast.domain.usecase

import com.maxi.skycast.domain.repository.WeatherRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(cityId: Int) =
        repository.deleteCity(cityId)
}