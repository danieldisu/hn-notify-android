package com.danieldisu.hnnotify.data.top

import com.danieldisu.hnnotify.data.common.StoryId

interface TopStoriesRepository {

  suspend fun get(): List<StoryId>

}
