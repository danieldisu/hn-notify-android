package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.common.noContent
import com.danieldisu.hnnotify.interests.AddInterestViewModel
import com.danieldisu.hnnotify.navigation.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            MaterialTheme {
                Scaffold(
                    bottomBar = bottomBar(navController),
                    floatingActionButton = actionButton(navController)
                ) {
                    NavHost(navController, startDestination = NavigationPath.topStories) {
                        NavigationGraph(navController, NoRetainViewModelOwner)
                    }
                }
            }
        }
    }

    private fun actionButton(navController: NavController): @Composable () -> Unit = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        when (navBackStackEntry.getCurrentFirstLevelScreen()) {
            FirstLevelScreen.TopStories -> noContent()
            FirstLevelScreen.Interests -> AddInterestFloatingButton(navController)
            null -> noContent()
        }
    }

    private fun bottomBar(navController: NavController): @Composable () -> Unit = {
        BottomAppBar {
            TopStoriesBottomBarIcon(navController)
            InterestsBottomBarIcon(navController)
        }
    }

    @Composable
    private fun AddInterestFloatingButton(navController: NavController) {
        FloatingActionButton(
            onClick = { navController.navigate(NavigationTarget.AddInterest) },
            icon = { Icon(asset = vectorResource(id = R.drawable.ic_add)) }
        )
    }

    @Composable
    private fun RowScope.InterestsBottomBarIcon(navController: NavController) =
        IconButton(
            modifier = Modifier.Companion.weight(1f),
            onClick = { navController.navigate(NavigationTarget.Interests) }
        ) { Icon(asset = vectorResource(id = R.drawable.ic_book)) }

    @Composable
    private fun RowScope.TopStoriesBottomBarIcon(navController: NavController) =
        IconButton(
            modifier = Modifier.Companion.weight(1f),
            onClick = { navController.navigate(NavigationTarget.TopStories) }
        ) { Icon(asset = vectorResource(id = R.drawable.ic_home)) }

    private fun addInterestViewModel(interestId: String?) =
        getViewModel(AddInterestViewModel::class, null) { parametersOf(interestId) }
}
