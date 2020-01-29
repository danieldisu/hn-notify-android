package com.danieldisu.hnnotify.presentation.stories.state

import com.danieldisu.hnnotify.data.stories.entities.Story

sealed class StoriesViewState {

  data class Loaded(
    val stories: List<Story>
  ) : StoriesViewState()

  object Empty : StoriesViewState()

}
