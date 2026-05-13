package com.maxi.skycast.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val updatedRequest = originalRequest
            .url
            .newBuilder()
            .addQueryParameter("appid", apiKey)
            .addQueryParameter("units", "metric")
            .build().let {
                originalRequest.newBuilder().url(it).build()
            }

        return chain.proceed(updatedRequest)
    }
}