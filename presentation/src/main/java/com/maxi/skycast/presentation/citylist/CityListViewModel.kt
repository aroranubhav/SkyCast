package com.maxi.skycast.presentation.citylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxi.skycast.domain.model.TemperatureUnit
import com.maxi.skycast.domain.repository.AppPreferencesDataStore
import com.maxi.skycast.domain.usecase.DeleteCityUseCase
import com.maxi.skycast.domain.usecase.GetSavedCitiesUseCase
import com.maxi.skycast.domain.usecase.RefreshAllCitiesWeatherUseCase
import com.maxi.skycast.domain.usecase.RefreshWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityListViewModel @Inject constructor(
    getSavedCitiesUseCase: GetSavedCitiesUseCase,
    private val deleteCityUseCase: DeleteCityUseCase,
    private val refreshWeatherUseCase: RefreshWeatherUseCase,
    private val refreshAllCitiesWeatherUseCase: RefreshAllCitiesWeatherUseCase,
    private val appPreferences: AppPreferencesDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(CityListUiState())
    val uiState: StateFlow<CityListUiState>
        get() = _uiState.asStateFlow()

    val temperatureUnit: StateFlow<TemperatureUnit> = appPreferences
        .temperatureUnit
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TemperatureUnit.CELSIUS
        )

    val syncFailed: StateFlow<Boolean> = appPreferences
        .syncFailed
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    init {
        getSavedCitiesUseCase()
            .onEach { cities ->
                _uiState.update {
                    it.copy(cities = cities)
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshWeather(cityId: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            refreshWeatherUseCase(cityId)
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.message
                        )
                    }
                }
        }
    }

    fun refreshAllCitiesWeather() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isRefreshing = true)
            }
            refreshAllCitiesWeatherUseCase()
                .onSuccess {
                    _uiState.update {
                        it.copy(isRefreshing = false)
                    }
                }
                .onFailure { e ->
                    _uiState.update {
                        it.copy(isRefreshing = false, errorMessage = e.message)
                    }
                }
        }
    }

    fun deleteCity(cityId: Int) {
        viewModelScope.launch {
            deleteCityUseCase(cityId)
        }
    }

    fun toggleTemperatureUnit() {
        viewModelScope.launch {
            val unit = if (temperatureUnit.value == TemperatureUnit.CELSIUS) {
                TemperatureUnit.FAHRENHEIT
            } else {
                TemperatureUnit.CELSIUS
            }
            appPreferences.saveTemperatureUnit(unit)
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(
                errorMessage = null
            )
        }
    }
}