package com.danieldisu.hnnotify.data.top.impl

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.top.TopStoriesRepository

class TopStoriesRepositoryImpl(
  private val topStoriesService: TopStoriesService
) : TopStoriesRepository {

  override suspend fun get(): List<StoryId> {
    return topStoriesService.get()
      .map { StoryId(it) }
  }

}
