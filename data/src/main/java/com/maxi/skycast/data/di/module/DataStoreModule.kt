package com.maxi.skycast.data.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.maxi.skycast.data.local.datastore.DefaultAppPreferencesDataStore
import com.maxi.skycast.domain.repository.AppPreferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val APP_PREFERENCES_FILE = "app_preferences"

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = {
                context.preferencesDataStoreFile(APP_PREFERENCES_FILE)
            }
        )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreBindingsModule {

    @Binds
    @Singleton
    abstract fun bindAppPreferences(
        impl: DefaultAppPreferencesDataStore
    ): AppPreferencesDataStore
}