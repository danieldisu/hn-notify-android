package com.danieldisu.hnnotify.main.bottombar

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.ComposableUnit
import com.danieldisu.hnnotify.common.noContent
import com.danieldisu.hnnotify.navigation.NavigationRoute
import com.danieldisu.hnnotify.navigation.NavigationRouteAddress
import com.danieldisu.hnnotify.navigation.navigate


/**
 * Having the bottom bar action button centralised makes easy to track changes in the routes and decide which behavior
 * should the button have on each tab. The main drawback is that you can't access any state of the current screen that
 * the user is seeing.
 *
 * If I would ever need to show the action button depending on some state of the current tab, I would have to change
 * the approach
 */
fun bottomBarActionButton(navController: NavController): ComposableUnit = {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()

    val routeAddress = navBackStackEntry?.destination?.route?.let { NavigationRouteAddress.fromRoute(it) }

    if (routeAddress == null) {
        noContent()
    } else {
        when (routeAddress) {
            NavigationRouteAddress.TopStories -> noContent()
            NavigationRouteAddress.Interests -> AddInterestActionButton(navController)
            NavigationRouteAddress.AddInterest -> noContent()
            NavigationRouteAddress.EditInterest -> noContent()
        }
    }
}

@Composable
fun AddInterestActionButton(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigate(NavigationRoute.AddInterest)
        }
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = "Add an interest")
    }
}
