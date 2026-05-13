package com.maxi.skycast.presentation.search

import com.maxi.skycast.domain.model.CitySearchResult

data class SearchUiState(
    val query: String = "",
    val results: List<CitySearchResult> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
    val isAdding: Boolean = false,
    val cityAdded: Boolean = false
)