package com.danieldisu.hnnotify.data.interesting.datasource

import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory

interface InterestingStoriesDataSource {

  fun save(interestingStory: InterestingStory)

  fun getAll(): List<InterestingStory>

}
