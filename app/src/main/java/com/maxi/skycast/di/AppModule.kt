package com.maxi.skycast.di

import com.maxi.skycast.BuildConfig
import com.maxi.skycast.data.di.qualifier.ApiKey
import com.maxi.skycast.data.di.qualifier.IsDebug
import com.maxi.skycast.domain.util.WidgetUpdater
import com.maxi.skycast.widget.SkyCastWidgetUpdater
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @ApiKey
    fun provideApiKey(): String =
        BuildConfig.API_KEY

    @Provides
    @IsDebug
    fun provideIsDebug(): Boolean =
        BuildConfig.DEBUG
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBindings {

    @Binds
    @Singleton
    abstract fun bindWidgetUpdater(
        impl: SkyCastWidgetUpdater
    ): WidgetUpdater
}