package com.danieldisu.hnnotify.data.stories

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.stories.entities.Story

interface StoryRepository {

  suspend fun getById(storyId: StoryId): Story?

}
