package com.danieldisu.hnnotify.data.interesting

import com.danieldisu.hnnotify.data.interesting.datasource.InterestingStoriesDataSource
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory

class InterestingStoriesRepository(
  private val dataSource: InterestingStoriesDataSource
) {

  suspend fun save(story: InterestingStory) = dataSource.save(story)

  suspend fun getAll(): List<InterestingStory> = dataSource.getAll()

}
