package com.danieldisu.hnnotify.data.stories.impl

import android.util.Log
import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story

class StoryRepositoryImpl(
  private val storyService: StoryService
) : StoryRepository {

  private val cache: MutableMap<StoryId, Story> = mutableMapOf()

  override suspend fun getById(storyId: StoryId): Story? {
    val cachedStory = cache[storyId]

    if (cachedStory != null) {
      return cachedStory
    }

    return try {
      storyService.getById(storyId.storyId)
        .also { cache[storyId] = it }
    } catch (exception: Exception) {
      Log.e("StoryRepository", null, exception)
      null
    }
  }

}
