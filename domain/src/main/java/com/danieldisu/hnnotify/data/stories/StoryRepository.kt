package com.danieldisu.hnnotify.data.stories

import com.danieldisu.hnnotify.data.entities.Story

class StoryRepository(
    private val storyApi: StoryApi
) {

    private val hardcodedUserId = "1"

    suspend fun getInterestingTopStories(): List<Story> =
        storyApi.getInterestingTopStories(hardcodedUserId).stories

}
