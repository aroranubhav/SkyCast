package com.maxi.skycast.presentation.widget

import com.maxi.skycast.data.local.dao.CityDao
import com.maxi.skycast.data.local.datastore.AppPreferencesDataStore
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetEntryPoint {

    fun cityDao(): CityDao
    fun appPreferencesDataStore(): AppPreferencesDataStore
}