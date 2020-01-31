package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase

class ScanInterestingStoriesUseCase(
  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase,
  private val interestsRepository: InterestsRepository,
  private val interestMatcher: InterestMatcher
) {

  suspend operator fun invoke(): Map<Interest, List<Story>> {
    val topStories = fetchTopStoriesUseCase()
    val interests = interestsRepository.getInterests()
    interestMatcher.build(interests)

    val stories = topStories.filter {
      interestMatcher.match(it.title)
    }

    return mapOf(interests[0] to stories)


//    return interestRegexes.map { interestToRegex ->
//      val interest = interestToRegex.key
//      val regex = interestToRegex.value
//
//      val storiesThatMatches = topStories.filter { story ->
//        regex.search(story.lowerCaseTitle)
//      }
//
//      interest to storiesThatMatches
//    }.toMap()
  }

}
