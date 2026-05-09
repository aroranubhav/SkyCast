package com.maxi.skycast.framework.di.module

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.maxi.skycast.BuildConfig
import com.maxi.skycast.data.remote.api.NetworkApiService
import com.maxi.skycast.data.remote.interceptor.AuthorizationInterceptor
import com.maxi.skycast.data.remote.interceptor.HttpLoggingInterceptorFactory
import com.maxi.skycast.framework.di.qualifier.ApiKey
import com.maxi.skycast.framework.di.qualifier.BaseUrl
import com.maxi.skycast.framework.di.qualifier.IsDebug
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String =
        "https://api.openweathermap.org/"

    @Provides
    @ApiKey
    fun provideApiKey(): String =
        BuildConfig.API_KEY

    @Provides
    @IsDebug
    fun provideIsDebug(): Boolean =
        BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            prettyPrint = true
        }

    @Provides
    @Singleton
    fun provideAuthorizationInterceptor(
        @ApiKey apiKey: String
    ): AuthorizationInterceptor =
        AuthorizationInterceptor(apiKey)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
        @IsDebug isDebug: Boolean
    ): HttpLoggingInterceptor =
        HttpLoggingInterceptorFactory(isDebug)
            .create()


    @Provides
    @Singleton
    fun provideOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authorizationInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        json: Json,
        httpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit
    }

    @Provides
    @Singleton
    fun provideNetworkService(
        retrofit: Retrofit
    ): NetworkApiService =
        retrofit.create(NetworkApiService::class.java)

}