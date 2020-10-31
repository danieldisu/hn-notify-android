package com.danieldisu.hnnotify.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.interests.InterestsScreen
import com.danieldisu.hnnotify.stories.TopStoriesScreen
import com.danieldisu.hnnotify.stories.TopStoriesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val topStoriesViewModel: TopStoriesViewModel by viewModel()
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = mainViewModel.stateFlow.collectAsState()

            MaterialTheme {
                Scaffold(
                    bottomBar = {
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
                ) {
                    when (state.value.selectedTab) {
                        Tab.TopStories -> TopStoriesScreen(topStoriesViewModel)
                        Tab.Interests -> InterestsScreen()
                    }
                }
            }
        }
    }
}
