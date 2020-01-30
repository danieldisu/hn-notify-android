package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase

class ScanInterestingStoriesUseCase(
  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase,
  private val interestsRepository: InterestsRepository,
  private val interestsRegexBuilder: InterestsRegexBuilder
) {

  suspend operator fun invoke(): Map<Interest, List<Story>> {
    val topStories = fetchTopStoriesUseCase()
    val interestRegexes = interestsRegexBuilder.build(interestsRepository.getInterests())

    return interestRegexes.map { interestToRegex ->
      val interest = interestToRegex.key
      val regex = interestToRegex.value

      println("testing against $regex")

      val storiesThatMatches = topStories.filter { story ->
        regex.search(story.lowerCaseTitle)
      }

      interest to storiesThatMatches
    }.toMap()
  }

}
