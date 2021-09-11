package com.danieldisu.hnnotify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danieldisu.hnnotify.interests.InterestDetailScreen
import com.danieldisu.hnnotify.interests.InterestsScreen
import com.danieldisu.hnnotify.interests.add.AddInterestScreen
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationGraph(navController: NavController) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = NavigationRoute.TopStories.getRouteValue()
    ) {

        NavigationRouteAddress.values().forEach { navigationRouteAddress ->
            when (navigationRouteAddress) {
                NavigationRouteAddress.TopStories -> {
                    composable(navigationRouteAddress.value) {
                        TopStoriesScreen(topStoriesViewModel = getViewModel())
                    }
                }
                NavigationRouteAddress.Interests -> {
                    composable(navigationRouteAddress.value) {
                        InterestsScreen(interestsViewModel = getViewModel(), navController)
                    }
                }
                NavigationRouteAddress.AddInterest -> {
                    composable(navigationRouteAddress.value) {
                        AddInterestScreen()
                    }
                }
                NavigationRouteAddress.EditInterest -> {
                    composable(navigationRouteAddress.value) {
                        val interestId = it.arguments?.getString("interestId")
                        InterestDetailScreen(viewModel = getViewModel(parameters = { parametersOf(interestId) }))
                    }
                }
            }
        }


    }
}
