package com.danieldisu.hnnotify.data.interesting.datasource

import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory
import kotlinx.coroutines.flow.Flow

interface InterestingStoriesDataSource {

  suspend fun save(interestingStory: InterestingStory)

  fun getAll(): Flow<List<InterestingStory>>

}
