package com.maxi.skycast.domain.util

import android.content.Context

interface WidgetUpdater {

    suspend fun update(context: Context)
}