package com.maxi.skycast.presentation.citylist

import com.maxi.skycast.domain.model.City

data class CityListUiState(
    val cities: List<City> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)
