package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchNewStoriesUseCase
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.domain.interesting.SaveInterestingStoryUseCase
import com.danieldisu.hnnotify.infrastructure.logging.TRACE
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ScanInterestingStoriesUseCase(
  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase,
  private val fetchNewStoriesUseCase: FetchNewStoriesUseCase,
  private val interestsRepository: InterestsRepository,
  private val interestMatcher: InterestMatcher,
  private val saveInterestingStoryUseCase: SaveInterestingStoryUseCase
) {

  suspend operator fun invoke(): ScanInterestingStoriesResult {
    TRACE("ScanInterestingStoriesUseCase::invoke")

    val stories = getNewAndTopStories()
    val interests = interestsRepository.getInterests()
    val interestingStories = InterestingStories()
    interestMatcher.build(interests)

    stories.forEach { story ->
      val matchingInterests = interestMatcher.matches(story.title)
      if (matchingInterests.isNotEmpty()) {
        TRACE("Found story with matching interests")
        interestingStories.add(story, matchingInterests)
        saveInterestingStoryUseCase.save(story, matchingInterests)
      }
    }

    if (interestingStories.isEmpty()) {
      TRACE("Found no matching stories for your interests")
      TRACE("Scanned stories ${stories.size}")
      TRACE("Added interests ${interests.size}")
    }

    return ScanInterestingStoriesResult(interestingStories.get())
  }

  private suspend fun getNewAndTopStories(): List<Story> {
    return coroutineScope {
      val topStories = async { fetchTopStoriesUseCase() }
      val newStories = async { fetchNewStoriesUseCase() }
      awaitAll(topStories, newStories)
    }.flatten().distinctBy { it.storyId }
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
