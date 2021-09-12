package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.danieldisu.hnnotify.main.bottombar.bottomBar
import com.danieldisu.hnnotify.main.bottombar.bottomBarActionButton
import com.danieldisu.hnnotify.navigation.NavigationGraph
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val bottomSheetNavigator = rememberBottomSheetNavigator()

            AddNavControllerToDependencyContainer(navController)

            navController.navigatorProvider += bottomSheetNavigator

            MaterialTheme {
                Scaffold(
                    bottomBar = bottomBar(navController),
                    floatingActionButton = bottomBarActionButton(navController)
                ) {
                    NavigationGraph(navController, bottomSheetNavigator)
                }
            }
        }
    }

    @Composable
    private fun AddNavControllerToDependencyContainer(navController: NavHostController) {
        SideEffect {
            loadKoinModules(module {
                single<NavController> { navController }
            })
        }
    }

}
