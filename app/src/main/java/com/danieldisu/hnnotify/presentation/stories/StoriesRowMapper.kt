package com.danieldisu.hnnotify.presentation.stories

import com.danieldisu.hnnotify.R
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.domain.interesting.InterestingStoryWithStoryData
import com.danieldisu.hnnotify.infrastructure.localization.StringResourceRepository
import com.danieldisu.hnnotify.presentation.stories.viewdata.StoryRow
import org.threeten.bp.LocalDate

class StoriesRowMapper(
  private val stringResourceRepository: StringResourceRepository
) {

  fun map(stories: List<InterestingStoryWithStoryData>): List<StoryRow> {
    val groupStoriesByDate = groupStoriesByDate(stories)

    return groupStoriesByDate.flatMap { mapEntry ->
      val categoryTitle = StoryRow.CategoryTitle(mapEntry.key)
      val storyTitles = mapEntry.value.map { StoryRow.StoryTitleRow(it) }
      listOf(categoryTitle).plus(storyTitles)
    }
  }

  private fun groupStoriesByDate(stories: List<InterestingStoryWithStoryData>): Map<String, List<Story>> {
    return stories
      .groupBy { it.interestingStory.foundAtDay }
      .toSortedMap(compareByDescending { it })
      .mapValues(this::mapToStory)
      .addTodayEntryIfNotPresentAlready()
      .mapKeys(this::toHumanReadableDate)
      .mapValues(this::sortByUpvoteCount)
  }

  private fun sortByUpvoteCount(mapEntry: Map.Entry<String, List<Story>>): List<Story> {
    val originalStories = mapEntry.value
    return originalStories.sortedByDescending { it.score }
  }

  private fun Map<LocalDate, List<Story>>.addTodayEntryIfNotPresentAlready(): Map<LocalDate, List<Story>> {
    val today = LocalDate.now()
    val todayAlreadyPresent = any { it.key == today }
    if (todayAlreadyPresent) {
      return this
    } else {
      return this
        .plus(today to emptyList())
    }
  }

  private fun mapToStory(entry: Map.Entry<LocalDate, List<InterestingStoryWithStoryData>>) =
    entry.value.map { it.story }

  private fun toHumanReadableDate(it: Map.Entry<LocalDate, List<Story>>): String {
    val storyDate = it.key
    val today = LocalDate.now()
    val yesterday = LocalDate.now().minusDays(1)
    return if (storyDate == today) {
      stringResourceRepository.get(R.string.label_today)
    } else if (storyDate == yesterday) {
      stringResourceRepository.get(R.string.label_yesterday)
    } else {
      storyDate.toString()
    }
  }

}
