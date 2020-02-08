package com.danieldisu.hnnotify.domain.interesting

import com.danieldisu.hnnotify.data.interesting.InterestingStoriesRepository
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.infrastructure.logging.LOG
import com.danieldisu.hnnotify.infrastructure.logging.TRACE
import kotlinx.coroutines.flow.*

class GetInterestingStoriesUseCase(
  private val interestingStoriesRepository: InterestingStoriesRepository,
  private val storyRepository: StoryRepository
) {

  fun get(): Flow<List<InterestingStoryWithStoryData>> {
    TRACE("GetInterestingStoriesUseCase::get")
    return interestingStoriesRepository
      .getAll()
      .map { getDetails(it) }
      .distinctUntilChanged()
      .onEach { TRACE("GetInterestingStoriesUseCase::onEach ${it.size}") }
      .onStart {
        LOG("Starting listening for interesting stories")
        TRACE("GetInterestingStoriesUseCase::onStart")
      }
  }

  private suspend fun getDetails(interestingStories: List<InterestingStory>): List<InterestingStoryWithStoryData> {
    return interestingStories
      .mapNotNull { interestingStory -> getDetails(interestingStory) }
      .distinctBy { it.interestingStory.storyId }
  }

  private suspend fun getDetails(interestingStory: InterestingStory): InterestingStoryWithStoryData? {
    return storyRepository.getById(interestingStory.storyId)
      ?.let { InterestingStoryWithStoryData(interestingStory, it) }
  }

}

data class InterestingStoryWithStoryData(
  val interestingStory: InterestingStory,
  val story: Story
)
