package com.danieldisu.hnnotify.data.interesting.entity

import com.danieldisu.hnnotify.data.common.StoryId
import org.threeten.bp.Instant

data class InterestingStory(
  val storyId: StoryId,
  val foundAt: Instant,
  val interestingFor: List<String>
)
