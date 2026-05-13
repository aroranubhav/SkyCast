package com.maxi.skycast.widget

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.maxi.skycast.domain.util.WidgetUpdater
import javax.inject.Inject

class SkyCastWidgetUpdater @Inject constructor(): WidgetUpdater {

    override suspend fun update(context: Context) {
        SkyCastWidget().updateAll(context)
    }
}