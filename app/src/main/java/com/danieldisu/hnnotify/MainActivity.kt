package com.danieldisu.hnnotify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import com.danieldisu.hnnotify.data.stories.StoryRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val storyRepository: StoryRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            val interestingTopStories = storyRepository.getInterestingTopStories()
            setContent {

                Column {
                    interestingTopStories.forEach {
                        Text(it.title)
                    }
                }
            }
        }
    }
}
