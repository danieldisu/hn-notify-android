package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.domain.interesting.SaveInterestingStoryUseCase
import com.danieldisu.hnnotify.infrastructure.logging.TRACE

class ScanInterestingStoriesUseCase(
  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase,
  private val interestsRepository: InterestsRepository,
  private val interestMatcher: InterestMatcher,
  private val saveInterestingStoryUseCase: SaveInterestingStoryUseCase
) {

  suspend operator fun invoke(): ScanInterestingStoriesResult {
    TRACE("ScanInterestingStoriesUseCase::invoke")
    val topStories = fetchTopStoriesUseCase()
    val interests = interestsRepository.getInterests()
    val interestingStories = InterestingStories()
    interestMatcher.build(interests)

    topStories.forEach { story ->
      val interests = interestMatcher.matches(story.title)
      if (interests.isNotEmpty()) {
        TRACE("Found story with matching interests")
        interestingStories.add(story, interests)
        saveInterestingStoryUseCase.save(story, interests)
      }
    }

    if (interestingStories.isEmpty()) {
      TRACE("Found no matching stories for your interests")
      TRACE("Scanned stories ${topStories.size}")
      TRACE("Added interests ${interests.size}")
    }

    return ScanInterestingStoriesResult(interestingStories.get())
  }

}

class InterestingStories {

  private val map: MutableMap<Interest, List<Story>> = mutableMapOf()

  fun add(story: Story, toInterests: List<Interest>) {
    toInterests.forEach { add(story, it) }
  }

  fun add(story: Story, toInterest: Interest) {
    val stories = map[toInterest] ?: listOf()
    map[toInterest] = stories.plus(story)
  }

  fun get(): Map<Interest, List<Story>> = map

  fun isEmpty(): Boolean = map.isEmpty()
}

data class ScanInterestingStoriesResult(
  val interestingStoriesByInterest: Map<Interest, List<Story>>
) {

  fun getStories(): List<Story> {
    return interestingStoriesByInterest
      .flatMapTo(mutableListOf()) { it.value }
      .distinct()
  }

}
