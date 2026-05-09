@file:OptIn(
    ExperimentalCoroutinesApi::class,
    FlowPreview::class
)

package com.maxi.skycast.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxi.skycast.domain.model.CitySearchResult
import com.maxi.skycast.domain.usecase.AddCityUseCase
import com.maxi.skycast.domain.usecase.SearchCitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val addCityUseCase: AddCityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState>
        get() = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        searchQuery
            .debounce(500)
            .filter {
                it.length >= 3
            }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                flow {
                    _uiState.update {
                        it.copy(isSearching = true)
                    }
                    searchCitiesUseCase(query)
                        .onSuccess {
                            emit(it)
                        }
                        .onFailure { e ->
                            _uiState.update {
                                it.copy(
                                    isSearching = false,
                                    errorMessage = e.message
                                )
                            }
                            emit(emptyList())
                        }
                }.onEach { results ->
                    _uiState.update {
                        it.copy(
                            results = results,
                            isSearching = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String) {
        _uiState.update {
            it.copy(query = query)
        }

        searchQuery.value = query

        if (query.length < 3) {
            _uiState.update {
                it.copy(results = emptyList())
            }
        }
    }

    fun addCity(city: CitySearchResult) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isAdding = true)
            }

            addCityUseCase(city)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isAdding = false,
                            cityAdded = true
                        )
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isAdding = false,
                            errorMessage = e.message
                        )
                    }
                }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}