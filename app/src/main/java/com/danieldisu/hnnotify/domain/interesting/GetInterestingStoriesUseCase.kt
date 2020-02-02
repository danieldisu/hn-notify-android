package com.danieldisu.hnnotify.domain.interesting

import com.danieldisu.hnnotify.data.interesting.InterestingStoriesRepository
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class GetInterestingStoriesUseCase(
  private val interestingStoriesRepository: InterestingStoriesRepository,
  private val storyRepository: StoryRepository
) {

  fun get(): Flow<List<Story>> {
    return interestingStoriesRepository
      .getAll()
      .map(getDetails())
  }

  private fun getDetails(): suspend (List<InterestingStory>) -> List<Story> =
    { list: List<InterestingStory> ->
      list
        .map { storyRepository.getById(it.storyId) }
        .filterNotNull()
        .distinctBy { it.storyId }
    }
}
