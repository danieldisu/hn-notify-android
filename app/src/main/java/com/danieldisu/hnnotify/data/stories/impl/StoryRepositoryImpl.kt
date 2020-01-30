package com.danieldisu.hnnotify.data.stories.impl

import android.util.Log
import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.datasource.StoryDBDatasource
import com.danieldisu.hnnotify.data.stories.datasource.toDBO
import com.danieldisu.hnnotify.data.stories.datasource.toStory
import com.danieldisu.hnnotify.data.stories.entities.Story

class StoryRepositoryImpl(
  private val storyService: StoryService,
  private val storyDBDatasource: StoryDBDatasource
) : StoryRepository {

  override suspend fun getById(storyId: StoryId): Story? {
    val cachedStory = storyDBDatasource.findById(storyId.storyId)?.toStory()

    if (cachedStory != null) {
      return cachedStory
    }

    return try {
      storyService.getById(storyId.storyId)
        .also { storyDBDatasource.insert(it.toDBO()) }
    } catch (exception: Exception) {
      Log.e("StoryRepository", null, exception)
      null
    }
  }

}
