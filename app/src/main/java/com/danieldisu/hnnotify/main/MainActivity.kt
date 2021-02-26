package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.danieldisu.hnnotify.navigation.AppNavigator
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val topStoriesViewModel: TopStoriesViewModel by inject()
    private val navigator: AppNavigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(
                    bottomBar = bottomBar(navigator),
                    floatingActionButton = actionButton(navigator)
                ) {
                    TopStoriesScreen(topStoriesViewModel = topStoriesViewModel)
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

    private fun bottomBar(appNavigator: AppNavigator): @Composable () -> Unit = {
//        BottomAppBar {
//            TopStoriesBottomBarIcon(navController)
//            InterestsBottomBarIcon(navController)
//        }
    }

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
