package com.maxi.skycast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.maxi.skycast.data.worker.WeatherSyncScheduler
import com.maxi.skycast.presentation.navigation.NavGraph
import com.maxi.skycast.presentation.ui.theme.SkyCastTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var weatherSyncScheduler: WeatherSyncScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        weatherSyncScheduler.schedule()
        setContent {
            SkyCastTheme {
                NavGraph(navController = rememberNavController())
            }
        }
    }
}