package com.danieldisu.hnnotify.stories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.setContent
import org.koin.androidx.viewmodel.ext.android.viewModel

class TopStoriesActivity : AppCompatActivity() {

    private val topStoriesViewModel: TopStoriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val state = topStoriesViewModel.stateFlow.collectAsState()
            MaterialTheme {
                TopStoriesScreen(
                    screenStateHolder = state,
                    onStoryClick = topStoriesViewModel::onStoryClick
                )
            }
        }
    }
}
