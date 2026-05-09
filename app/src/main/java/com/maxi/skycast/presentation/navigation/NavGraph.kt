package com.maxi.skycast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maxi.skycast.presentation.citylist.CityListScreen
import com.maxi.skycast.presentation.search.SearchScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.CityList.route
    ) {
        composable(Screen.CityList.route) {
            CityListScreen(
                onNavigateToSearch = { navController.navigate(Screen.Search.route) }
            )
        }
        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
