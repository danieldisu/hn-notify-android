package com.danieldisu.hnnotify.stories

import com.danieldisu.hnnotify.data.stories.Story

sealed class TopStoriesScreenState {
    object Initial : TopStoriesScreenState()
    data class Loaded(val stories: List<Story>) : TopStoriesScreenState()
    data class Error(val error: Throwable) : TopStoriesScreenState()
    object Loading : TopStoriesScreenState()
}
