package com.danieldisu.hnnotify.domain.fetch

import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.data.top.TopStoriesRepository
import com.danieldisu.hnnotify.infrastructure.common.parallelMap
import com.danieldisu.hnnotify.infrastructure.logging.LOG
import com.danieldisu.hnnotify.infrastructure.logging.TRACE

class FetchTopStoriesUseCase(
  private val topStoriesRepository: TopStoriesRepository,
  private val storyRepository: StoryRepository
) {

  suspend operator fun invoke(): List<Story> {
    LOG("Fetching Top Stories")
    TRACE("FetchTopStoriesUseCase::init")

    val topStoriesIds = topStoriesRepository.get()

    TRACE("FetchTopStoriesUseCase::fetched ${topStoriesIds.size} topstories")

    return topStoriesIds.parallelMap {
      storyRepository.getById(it)
    }.filterNotNull()
      .apply { TRACE("FetchTopStoriesUseCase::finished") }
  }

}
