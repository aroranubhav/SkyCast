package com.maxi.skycast.data.di.module

import com.maxi.skycast.data.repository.DefaultWeatherRepository
import com.maxi.skycast.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: DefaultWeatherRepository
    ): WeatherRepository
}