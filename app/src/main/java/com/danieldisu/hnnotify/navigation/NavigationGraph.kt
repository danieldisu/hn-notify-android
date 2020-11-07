package com.danieldisu.hnnotify.navigation

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.danieldisu.hnnotify.interests.AddInterestScreen
import com.danieldisu.hnnotify.interests.AddInterestViewModel
import com.danieldisu.hnnotify.interests.InterestsScreen
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.NavigationGraph(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    topStoriesNavigationPath(navController, viewModelStoreOwner)
    addInterestNavigationPath(navController, viewModelStoreOwner)
    interestNavigationPath(navController, viewModelStoreOwner)
    editInterestNavigationPath(navController, viewModelStoreOwner)
}

private fun NavGraphBuilder.topStoriesNavigationPath(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) = composable(NavigationPath.topStories) {
    TopStoriesScreen(navController, viewModelStoreOwner.getViewModel())
}

private fun NavGraphBuilder.addInterestNavigationPath(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) = composable(NavigationPath.addInterest) {
    AddInterestScreen(navController, viewModelStoreOwner.buildViewModel(null))
}

private fun NavGraphBuilder.interestNavigationPath(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    composable(NavigationPath.interests) {
        InterestsScreen(navController, viewModelStoreOwner.getViewModel())
    }
}

private fun NavGraphBuilder.editInterestNavigationPath(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    val argumentId = "interestId"
    composable(
        route = NavigationPath.editInterest,
        arguments = listOf(navArgument(argumentId) { this.type = NavType.StringType })
    ) {
        val interestId = it.getStringArgument(argumentId)
        AddInterestScreen(
            navController = navController,
            viewModel = viewModelStoreOwner.buildViewModel(interestId)
        )
    }
}

fun ViewModelStoreOwner.buildViewModel(interestId: String?): AddInterestViewModel {
    return getViewModel { parametersOf(interestId) }
}
