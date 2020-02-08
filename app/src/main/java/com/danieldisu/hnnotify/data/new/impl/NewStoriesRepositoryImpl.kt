package com.danieldisu.hnnotify.data.new.impl

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.new.NewStoriesRepository

class NewStoriesRepositoryImpl(
  private val newtoriesService: NewStoriesService
) : NewStoriesRepository {

  override suspend fun get(): List<StoryId> {
    return newtoriesService.get()
      .map {
        StoryId(it)
      }
  }
}
