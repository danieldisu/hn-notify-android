package com.danieldisu.hnnotify.domain.interesting

import com.danieldisu.hnnotify.data.interesting.InterestingStoriesRepository
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.infrastructure.logging.LOG
import org.threeten.bp.Instant

class SaveInterestingStoryUseCase(
  private val interestingStoriesRepository: InterestingStoriesRepository
) {

  suspend fun save(story: Story, interests: List<Interest>) {
    LOG("SaveInterestingStoryUseCase::save id: ${story.storyId} title: ${story.title} matching because of interests $interests")
    interestingStoriesRepository.save(
      InterestingStory(
        story.storyId,
        foundAt = Instant.now(),
        interestingFor = interests.map { it.id }
      )
    )
  }

}
