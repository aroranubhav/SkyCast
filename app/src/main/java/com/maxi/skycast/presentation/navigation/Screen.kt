package com.maxi.skycast.presentation.navigation

sealed class Screen(
    val route: String
) {

    data object CityList : Screen("city_list")
    data object Search : Screen("search")
}