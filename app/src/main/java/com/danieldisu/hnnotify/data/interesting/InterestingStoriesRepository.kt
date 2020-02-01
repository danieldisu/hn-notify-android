package com.danieldisu.hnnotify.data.interesting

import com.danieldisu.hnnotify.data.interesting.datasource.InterestingStoriesDataSource
import com.danieldisu.hnnotify.data.interesting.entity.InterestingStory

class InterestingStoriesRepository(
  private val dataSource: InterestingStoriesDataSource
) {

  fun save(story: InterestingStory) = dataSource.save(story)

  fun getAll(): List<InterestingStory> = dataSource.getAll()

}
