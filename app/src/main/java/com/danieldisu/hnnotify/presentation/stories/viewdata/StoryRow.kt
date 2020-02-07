package com.danieldisu.hnnotify.presentation.stories.viewdata

import com.danieldisu.hnnotify.data.stories.entities.Story

sealed class StoryRow {

  data class StoryTitleRow(val story: Story) : StoryRow()

  data class CategoryTitle(val value: String) : StoryRow()
}
