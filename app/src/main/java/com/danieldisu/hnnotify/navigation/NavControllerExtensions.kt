package com.danieldisu.hnnotify.navigation

import androidx.navigation.NavController

fun NavController.navigate(navigationRoute: NavigationRoute) {
    navigate(navigationRoute.getRouteValue())
}
