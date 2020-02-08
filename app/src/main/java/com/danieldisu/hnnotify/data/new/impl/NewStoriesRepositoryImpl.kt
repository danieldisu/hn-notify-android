package com.danieldisu.hnnotify.data.new.impl

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.new.NewStoriesRepository

private const val LIMIT_NUMBER_STORIES = 100

class NewStoriesRepositoryImpl(
  private val newStoriesService: NewStoriesService
) : NewStoriesRepository {

  override suspend fun get(): List<StoryId> {
    return newStoriesService.get()
      .take(LIMIT_NUMBER_STORIES)
      .map {
        StoryId(it)
      }
  }
}
