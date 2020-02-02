package com.danieldisu.hnnotify.data.interesting

import com.danieldisu.hnnotify.data.interesting.datasource.InterestingStoriesDataSource
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import kotlinx.coroutines.flow.Flow

class InterestingStoriesRepository(
  private val dataSource: InterestingStoriesDataSource
) {

  suspend fun save(story: InterestingStory) = dataSource.save(story)

  fun getAll(): Flow<List<InterestingStory>> = dataSource.getAll()

}
