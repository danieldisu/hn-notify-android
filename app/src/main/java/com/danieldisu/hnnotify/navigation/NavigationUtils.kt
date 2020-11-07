package com.danieldisu.hnnotify.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.KEY_ROUTE
import androidx.navigation.compose.navigate

fun NavBackStackEntry?.getCurrentFirstLevelScreen(): FirstLevelScreen? {
    return if (this == null) null
    else {
        val route = this.arguments?.getString(KEY_ROUTE)
        FirstLevelScreen.from(route)
    }
}

fun NavController.navigate(navigationTarget: NavigationTarget) {
    navigate(navigationTarget.navigationPath)
}

fun NavBackStackEntry.getStringArgument(argumentId: String): String =
    arguments?.getString(argumentId)
        ?: throw IllegalNavigationException("No argument found for key $argumentId")

data class IllegalNavigationException(override val message: String) : Throwable(message)
