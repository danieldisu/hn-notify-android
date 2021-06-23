package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.navigation.AppNavigator
import com.danieldisu.hnnotify.navigation.NavigationGraph
import com.danieldisu.hnnotify.navigation.NavigationRoute
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val navigator: AppNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            val bottomBarScreens = listOf(
                TopStoriesBottomBarScreen,
                InterestBottomBarScreen,
            )

            MaterialTheme {
                Scaffold(
                    bottomBar = bottomBar(navController, bottomBarScreens),
                    floatingActionButton = actionButton(navigator)
                ) {
                    NavigationGraph(navController = navController)
                }
            }
        }
    }

    private fun actionButton(appNavigator: AppNavigator): @Composable () -> Unit = {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        when (navBackStackEntry.getCurrentFirstLevelScreen()) {
//            FirstLevelScreen.TopStories -> noContent()
//            FirstLevelScreen.Interests -> AddInterestFloatingButton(navController)
//            null -> noContent()
//        }
    }

    private fun bottomBar(
        navController: NavController,
        screens: List<BottomBarScreen>,
    ): @Composable () -> Unit = {
        BottomNavigation {
            val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { screen ->
                BottomBarNavigationScreen(navController, screen, currentRoute)
            }

        }
    }

    @Composable
    private fun RowScope.BottomBarNavigationScreen(
        navController: NavController,
        bottomBarScreen: BottomBarScreen,
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

    abstract class BottomBarScreen(
        val route: NavigationRoute,
        @StringRes val label: Int,
        @DrawableRes val icon: Int,
    )

    object InterestBottomBarScreen : BottomBarScreen(
        route = NavigationRoute.Interests,
        label = R.string.bottom_bar_label_interests,
        icon = R.drawable.ic_book
    )

    object TopStoriesBottomBarScreen : BottomBarScreen(
        route = NavigationRoute.TopStories,
        label = R.string.bottom_bar_label_stories,
        icon = R.drawable.ic_home
    )


//    @Composable
//    private fun AddInterestFloatingButton(navController: NavController) {
//        FloatingActionButton(
//            onClick = { navController.navigate(NavigationTarget.AddInterest) },
//            icon = { Icon(asset = vectorResource(id = R.drawable.ic_add)) }
//        )
//    }
//
//    @Composable
//    private fun RowScope.InterestsBottomBarIcon(navController: NavController) =
//        IconButton(
//            modifier = Modifier.Companion.weight(1f),
//            onClick = { navController.navigate(NavigationTarget.Interests) }
//        ) { Icon(asset = vectorResource(id = R.drawable.ic_book)) }
//
//    @Composable
//    private fun RowScope.TopStoriesBottomBarIcon(navController: NavController) =
//        IconButton(
//            modifier = Modifier.Companion.weight(1f),
//            onClick = { navController.navigate(NavigationTarget.TopStories) }
//        ) { Icon(asset = vectorResource(id = R.drawable.ic_home)) }
//
//    private fun addInterestViewModel(interestId: String?) =
//        getViewModel(AddInterestViewModel::class, null) { parametersOf(interestId) }
}
