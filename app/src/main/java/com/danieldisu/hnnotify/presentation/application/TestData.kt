package com.danieldisu.hnnotify.presentation.application

import com.danieldisu.hnnotify.data.common.StoryId
import com.danieldisu.hnnotify.data.interesting.InterestingStoriesRepository
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import org.threeten.bp.Instant
import org.threeten.bp.Period

class TestData(
  private val interestingStoriesRepository: InterestingStoriesRepository
) {

  private val interestingStories = listOf(
    InterestingStory(
      StoryId("22188420"),
      Instant.now().minus(Period.ofDays(1)),
      interestingFor = listOf("1")
    ),
    InterestingStory(
      StoryId("22201316"),
      Instant.now().minus(Period.ofDays(2)),
      interestingFor = listOf("1")
    ),
    InterestingStory(
      StoryId("22159773"),
      Instant.now().minus(Period.ofDays(3)),
      interestingFor = listOf("1")
    )
  )

  suspend fun addTestData() {
    interestingStories.forEach { interestingStoriesRepository.save(it) }
  }

}
