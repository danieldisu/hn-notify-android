package com.danieldisu.hnnotify.domain.interesting

import com.danieldisu.hnnotify.data.interesting.InterestingStoriesRepository
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetInterestingStoriesUseCase(
  private val interestingStoriesRepository: InterestingStoriesRepository,
  private val storyRepository: StoryRepository
) {

  fun get(): Flow<List<InterestingStoryWithStoryData>> {
    return interestingStoriesRepository
      .getAll()
      .map(getDetails())
  }

  private fun getDetails(): suspend (List<InterestingStory>) -> List<InterestingStoryWithStoryData> =
    { list: List<InterestingStory> ->
      list
        .map { interestingStory ->
          storyRepository.getById(interestingStory.storyId)
            .let { story ->
              if (story != null) {
                InterestingStoryWithStoryData(interestingStory, story)
              } else {
                null
              }
            }
        }
        .filterNotNull()
        .distinctBy { it.interestingStory.storyId }
    }
}

data class InterestingStoryWithStoryData(
  val interestingStory: InterestingStory,
  val story: Story
)
