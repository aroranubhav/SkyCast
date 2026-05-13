package com.maxi.skycast.domain.usecase

import com.maxi.skycast.domain.model.CitySearchResult
import com.maxi.skycast.domain.repository.WeatherRepository
import javax.inject.Inject

class SearchCitiesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    suspend operator fun invoke(query: String): Result<List<CitySearchResult>> =
        repository.searchCities(query.trim())
}