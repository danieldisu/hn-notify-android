package com.danieldisu.hnnotify.data.interesting.entity

import com.danieldisu.hnnotify.data.common.StoryId
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset

data class InterestingStory(
  val storyId: StoryId,
  val foundAt: Instant,
  val interestingFor: List<String>
) {

  val foundAtDay: LocalDate = LocalDateTime.ofInstant(foundAt, ZoneOffset.UTC).toLocalDate()

}
