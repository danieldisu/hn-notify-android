package com.danieldisu.hnnotify.data.top.impl

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.top.TopStoriesRepository

private const val LIMIT_NUMBER_STORIES = 100

class TopStoriesRepositoryImpl(
  private val topStoriesService: TopStoriesService
) : TopStoriesRepository {

  override suspend fun get(): List<StoryId> {
    return topStoriesService.get()
      .take(LIMIT_NUMBER_STORIES)
      .map { StoryId(it) }
  }

}
