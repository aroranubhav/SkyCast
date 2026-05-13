package com.maxi.skycast.domain.repository

import com.maxi.skycast.domain.model.TemperatureUnit
import kotlinx.coroutines.flow.Flow

interface AppPreferencesDataStore {

    val temperatureUnit: Flow<TemperatureUnit>
    val syncFailed: Flow<Boolean>
    suspend fun saveTemperatureUnit(unit: TemperatureUnit)
    suspend fun saveSyncFailed(status: Boolean)
}