package com.danieldisu.hnnotify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danieldisu.hnnotify.interests.AddInterestScreen
import com.danieldisu.hnnotify.interests.InterestsScreen
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
                        InterestsScreen(interestsViewModel = getViewModel())
                    }
                }
                NavigationRouteAddress.AddInterest -> {
                    composable(navigationRouteAddress.value) {
                        AddInterestScreen(viewModel = getViewModel(parameters = { parametersOf(null) }))
                    }
                }
                NavigationRouteAddress.EditInterest -> {
                    composable(navigationRouteAddress.value) {
                        val interestId = it.arguments?.getString("interestId")
                        AddInterestScreen(viewModel = getViewModel(parameters = { parametersOf(interestId) }))
                    }
                }
            }
        }


    }
}
