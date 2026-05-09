package com.maxi.skycast.framework.di.module

import android.content.Context
import androidx.work.WorkManager
import com.maxi.skycast.data.worker.WeatherSyncScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideWeatherSyncScheduler(
        workManager: WorkManager
    ): WeatherSyncScheduler =
        WeatherSyncScheduler(workManager)
}