package com.maxi.skycast.data.di.module

import android.content.Context
import androidx.room.Room
import com.maxi.skycast.data.local.dao.CityDao
import com.maxi.skycast.data.local.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    const val WEATHER_DATABASE = "weather_database"

    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context
    ): WeatherDatabase =
        Room
            .databaseBuilder(
                context,
                WeatherDatabase::class.java,
                WEATHER_DATABASE
            )
            .build()

    @Provides
    @Singleton
    fun provideCityDao(
        database: WeatherDatabase
    ): CityDao =
        database.cityDao()
}