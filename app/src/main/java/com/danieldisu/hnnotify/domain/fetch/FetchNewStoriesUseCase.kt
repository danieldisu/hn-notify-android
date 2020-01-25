package com.danieldisu.hnnotify.domain.fetch

import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.data.top.TopStoriesRepository

private const val MAX_NUMBER_OF_STORIES_TO_FETCH = 10

class FetchNewStoriesUseCase(
  private val topStoriesRepository: TopStoriesRepository,
  private val storyRepository: StoryRepository
) {

  suspend operator fun invoke(): List<Story> {

    val topStoriesIds = topStoriesRepository.get()
      .take(MAX_NUMBER_OF_STORIES_TO_FETCH)

    return topStoriesIds.map {
      storyRepository.getById(it)
    }
  }

}
