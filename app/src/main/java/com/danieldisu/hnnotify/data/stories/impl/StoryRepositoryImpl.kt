package com.danieldisu.hnnotify.data.stories.impl

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story

class StoryRepositoryImpl(
  private val storyService: StoryService
) : StoryRepository {

  override suspend fun getById(storyId: StoryId): Story {
    return storyService.getById(storyId.storyId)
  }

}
