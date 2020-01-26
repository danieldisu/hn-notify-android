package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import java.util.*

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

      val storiesThatMatches = topStories.filter { story ->
        regex.containsMatchIn(story.title.toLowerCase(Locale.getDefault()))
      }

      interest to storiesThatMatches
    }.toMap()
  }

}
