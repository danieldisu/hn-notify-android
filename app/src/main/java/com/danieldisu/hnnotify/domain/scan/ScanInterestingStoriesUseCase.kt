package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.domain.interesting.SaveInterestingStoryUseCase

class ScanInterestingStoriesUseCase(
  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase,
  private val interestsRepository: InterestsRepository,
  private val interestMatcher: InterestMatcher,
  private val saveInterestingStoryUseCase: SaveInterestingStoryUseCase
) {

  suspend operator fun invoke(): ScanInterestingStoriesResult {
    val topStories = fetchTopStoriesUseCase()
    val interests = interestsRepository.getInterests()
    val interestingStories = InterestingStories()
    interestMatcher.build(interests)

    topStories.forEach { story ->
      val interests = interestMatcher.matches(story.title)
      interestingStories.add(story, interests)
      saveInterestingStoryUseCase.save(story, interests)
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
