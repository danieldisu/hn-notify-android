package com.danieldisu.hnnotify.main.bottombar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.noContent
import com.danieldisu.hnnotify.navigation.NavigationRoute
import com.danieldisu.hnnotify.navigation.NavigationRouteAddress

private val bottomBarItems = listOf(
    BottomBarItem(
        route = NavigationRoute.TopStories,
        label = R.string.bottom_bar_label_stories,
        icon = R.drawable.ic_home
    ),
    BottomBarItem(
        route = NavigationRoute.Interests,
        label = R.string.bottom_bar_label_interests,
        icon = R.drawable.ic_book
    ),
)


fun bottomBar(
    navController: NavController,
): @Composable () -> Unit = {
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routeAddress = navBackStackEntry?.destination?.route?.let { NavigationRouteAddress.fromRoute(it) }

    when (routeAddress) {
        NavigationRouteAddress.TopStories -> HNBottomBar(navController, currentRoute)
        NavigationRouteAddress.Interests -> HNBottomBar(navController, currentRoute)
        NavigationRouteAddress.AddInterest -> noContent()
        NavigationRouteAddress.EditInterest -> noContent()
        null -> noContent()
    }
}

@Composable
private fun HNBottomBar(
    navController: NavController,
    currentRoute: String?,
) {
    BottomNavigation {
        bottomBarItems.forEach { screen ->
            HNBottomNavigationItem(navController, screen, currentRoute)
        }
    }
}

@Composable
private fun RowScope.HNBottomNavigationItem(
    navController: NavController,
    bottomBarScreen: BottomBarItem,
    currentRoute: String?,
) {
    BottomNavigationItem(
        icon = { Icon(painter = painterResource(id = bottomBarScreen.icon), contentDescription = null) },
        label = { Text(stringResource(bottomBarScreen.label)) },
        selected = currentRoute == bottomBarScreen.route.getRouteValue(),
        onClick = {
            navController.navigate(bottomBarScreen.route.getRouteValue()) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}

private data class BottomBarItem(
    val route: NavigationRoute,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
)
