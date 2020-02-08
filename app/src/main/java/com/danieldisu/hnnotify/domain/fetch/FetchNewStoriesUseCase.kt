package com.danieldisu.hnnotify.domain.fetch

import com.danieldisu.hnnotify.data.new.NewStoriesRepository
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.infrastructure.common.parallelMap
import com.danieldisu.hnnotify.infrastructure.logging.LOG
import com.danieldisu.hnnotify.infrastructure.logging.TRACE

class FetchNewStoriesUseCase(
  private val newStoriesRepository: NewStoriesRepository,
  private val storyRepository: StoryRepository
) {

  suspend operator fun invoke(): List<Story> {
    LOG("Fetching New Stories")
    TRACE("FetchNewStoriesUseCase::init")

    val newStoriesIds = newStoriesRepository.get()

    TRACE("FetchNewStoriesUseCase::fetched ${newStoriesIds.size} new stories")

    return newStoriesIds.parallelMap {
      storyRepository.getById(it)
    }.filterNotNull()
      .apply { TRACE("FetchNewStoriesUseCase::finished") }
  }

}
