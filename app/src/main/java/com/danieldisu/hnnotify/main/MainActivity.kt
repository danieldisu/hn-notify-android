package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.interests.InterestsScreen
import com.danieldisu.hnnotify.interests.InterestsViewModel
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val topStoriesViewModel: TopStoriesViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()
    private val interestsViewModel: InterestsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = mainViewModel.stateFlow.collectAsState()

            MaterialTheme {
                Scaffold(
                    bottomBar = bottomBar(),
                    floatingActionButton = actionButton(state.value.selectedTab)
                ) {
                    when (state.value.selectedTab) {
                        Tab.TopStories -> TopStoriesScreen(topStoriesViewModel)
                        Tab.Interests -> InterestsScreen(interestsViewModel)
                    }
                }
            }
        }
    }

    private fun actionButton(selectedTab: Tab): @Composable () -> Unit = {
        when (selectedTab) {
            Tab.TopStories -> {
            }
            Tab.Interests -> FloatingActionButton(
                onClick = interestsViewModel::onActionButtonClick,
                icon = {
                    Icon(
                        asset = vectorResource(id = R.drawable.ic_add)
                    )
                }
            )
        }
    }

    private fun bottomBar(): @Composable () -> Unit = {
        BottomAppBar {
            IconButton(
                modifier = Modifier.weight(1f),
                onClick = mainViewModel::onHomeIconClick
            ) {
                Icon(
                    asset = vectorResource(id = R.drawable.ic_home)
                )
            }

            IconButton(
                modifier = Modifier.weight(1f),
                onClick = mainViewModel::onInterestsButtonClick
            ) {
                Icon(
                    asset = vectorResource(id = R.drawable.ic_book)
                )
            }
        }
    }
}
