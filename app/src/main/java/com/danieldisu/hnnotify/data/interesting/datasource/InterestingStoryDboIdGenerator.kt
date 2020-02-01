package com.danieldisu.hnnotify.data.interesting.datasource

import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory

object InterestingStoryDboIdGenerator {

  fun generate(story: InterestingStory): String {

    val id = story.storyId.hashCode()
    val interests = story.interestingFor.joinToString().hashCode()
    val at = story.foundAt.hashCode()

    return "$id-$interests-$at"
  }

}
