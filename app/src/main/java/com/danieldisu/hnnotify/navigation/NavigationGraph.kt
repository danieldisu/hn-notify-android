package com.danieldisu.hnnotify.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.danieldisu.hnnotify.interests.InterestDetailScreen
import com.danieldisu.hnnotify.interests.InterestsScreen
import com.danieldisu.hnnotify.interests.add.AddInterestScreen
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationGraph(navController: NavController, bottomSheetNavigator: BottomSheetNavigator) {
    ModalBottomSheetLayout(bottomSheetNavigator) {
        NavHost(
            navController = navController as NavHostController,
            startDestination = NavigationRoute.TopStories.getRouteValue()
        ) {
            NavigationRouteAddress.values().forEach { setupRoute(it, navController) }
        }
    }
}

private fun NavGraphBuilder.setupRoute(
    navigationRouteAddress: NavigationRouteAddress,
    navController: NavController,
) {
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
            bottomSheet(navigationRouteAddress.value) {
                val interestId = it.arguments?.getString("interestId")
                InterestDetailScreen(viewModel = getViewModel(parameters = { parametersOf(interestId) }))
            }
        }
    }
}
