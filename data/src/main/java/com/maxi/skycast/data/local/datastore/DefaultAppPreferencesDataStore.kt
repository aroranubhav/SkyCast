package com.maxi.skycast.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.maxi.skycast.domain.model.TemperatureUnit
import com.maxi.skycast.domain.repository.AppPreferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultAppPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
): AppPreferencesDataStore {

    companion object {
        private val TEMPERATURE_UNIT_KEY = stringPreferencesKey("temperature_unit")
        private val SYNC_FAILED_KEY = booleanPreferencesKey("sync_failed")
    }

    override val temperatureUnit: Flow<TemperatureUnit> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val unit = preferences[TEMPERATURE_UNIT_KEY] ?: TemperatureUnit.CELSIUS.name
            TemperatureUnit.valueOf(unit)
        }

    override suspend fun saveTemperatureUnit(unit: TemperatureUnit) {
        dataStore.edit { preferences ->
            preferences[TEMPERATURE_UNIT_KEY] = unit.name
        }
    }

    override val syncFailed: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[SYNC_FAILED_KEY] ?: false
        }

    override suspend fun saveSyncFailed(status: Boolean) {
        dataStore.edit { preferences ->
            preferences[SYNC_FAILED_KEY] = status
        }
    }
}