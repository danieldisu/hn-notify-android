package com.danieldisu.hnnotify.data.interesting.datasource

import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory

interface InterestingStoriesDataSource {

  suspend fun save(interestingStory: InterestingStory)

  suspend fun getAll(): List<InterestingStory>

}
