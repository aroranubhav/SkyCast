package com.maxi.skycast.presentation.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.maxi.skycast.MainActivity
import com.maxi.skycast.data.mapper.toDomain
import com.maxi.skycast.domain.model.City
import com.maxi.skycast.domain.model.TemperatureUnit
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.first

class SkyCastWidget : GlanceAppWidget() {

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {
        val entryPoint = EntryPointAccessors.fromApplication(
            context, WidgetEntryPoint::class.java
        )

        val cities = entryPoint.cityDao()
            .getAllCities()
            .first()

        val firstCity = cities.firstOrNull()?.toDomain()

        val temperatureUnit = entryPoint
            .appPreferencesDataStore()
            .temperatureUnit
            .first()

        provideContent {
            GlanceTheme {
                WidgetContent(
                    city = firstCity,
                    temperatureUnit = temperatureUnit
                )
            }
        }
    }
}

@Composable
fun WidgetContent(
    city: City?,
    temperatureUnit: TemperatureUnit
) {
    val launchIntent = actionStartActivity<MainActivity>()

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(GlanceTheme.colors.surface)
            .padding(16.dp)
            .appWidgetBackground()
            .clickable(launchIntent),
        contentAlignment = Alignment.Center
    ) {
        when {
            city == null -> EmptyWidgetContent()
            city.weather == null -> LoadingWidgetContent(city = city.name)
            else -> WeatherWidgetContent(
                city = city,
                temperatureUnit = temperatureUnit
            )
        }
    }
}

@Composable
fun EmptyWidgetContent() {
    Column(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SkyCast",
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = GlanceTheme.colors.onSurface
            )
        )
        Spacer(modifier = GlanceModifier.height(4.dp))
        Text(
            text = "Add city to get started",
            style = TextStyle(
                fontSize = 12.sp,
                color = GlanceTheme.colors.onSurfaceVariant
            )
        )
    }
}

@Composable
fun LoadingWidgetContent(city: String) {
    Column(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = city,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = GlanceTheme.colors.onSurface
            )
        )
        Spacer(modifier = GlanceModifier.height(4.dp))
        Text(
            text = "Fetching Weather...",
            style = TextStyle(
                fontSize = 12.sp,
                color = GlanceTheme.colors.onSurfaceVariant
            )
        )
    }
}

@Composable
fun WeatherWidgetContent(
    city: City,
    temperatureUnit: TemperatureUnit
) {
    val weather = city.weather!!

    Column(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "${city.name}, ${city.country}",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = GlanceTheme.colors.onSurfaceVariant
            )
        )
        Spacer(modifier = GlanceModifier.height(4.dp))
        Text(
            text = temperatureUnit.display(weather.temperature),
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = GlanceTheme.colors.onSurface
            )
        )
        Spacer(modifier = GlanceModifier.height(2.dp))
        Text(
            text = weather.description.replaceFirstChar { it.uppercase() },
            style = TextStyle(
                fontSize = 12.sp,
                color = GlanceTheme.colors.onSurfaceVariant
            )
        )
        Spacer(modifier = GlanceModifier.height(4.dp))
        Row {
            Text(
                text = "H: ${temperatureUnit.display(weather.maxTemp)}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = GlanceTheme.colors.onSurfaceVariant
                )
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = "L: ${temperatureUnit.display(weather.minTemp)}",
                style = TextStyle(
                    fontSize = 12.sp,
                    color = GlanceTheme.colors.onSurfaceVariant
                )
            )
        }
    }
}