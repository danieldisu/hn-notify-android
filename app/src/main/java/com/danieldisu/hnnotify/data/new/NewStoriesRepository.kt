package com.danieldisu.hnnotify.data.new

import com.danieldisu.hnnotify.data.common.StoryId

interface NewStoriesRepository {

  suspend fun get(): List<StoryId>

}
