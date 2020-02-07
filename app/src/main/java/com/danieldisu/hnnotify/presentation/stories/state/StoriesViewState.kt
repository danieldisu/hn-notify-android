package com.danieldisu.hnnotify.presentation.stories.state

import com.danieldisu.hnnotify.presentation.stories.viewdata.StoryRow

sealed class StoriesViewState {

  data class Loaded(
    val rows: List<StoryRow>
  ) : StoriesViewState()

  object Empty : StoriesViewState()

}
