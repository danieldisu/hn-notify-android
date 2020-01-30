package com.danieldisu.hnnotify.testutils.builders

import com.danieldisu.hnnotify.data.stories.entities.Story

object StoryBuilder {

  fun story(title: String): Story {
    return Story(
      id = "oneId",
      by = "",
      score = 0,
      title = title,
      url = null,
      type = "story",
      time = 0L,
      text = null
    )
  }

}
