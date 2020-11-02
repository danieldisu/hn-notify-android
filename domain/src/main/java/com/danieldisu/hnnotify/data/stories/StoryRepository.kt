package com.danieldisu.hnnotify.data.stories

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.core.mapValue
import com.danieldisu.hnnotify.data.entities.Story
import com.slack.eithernet.ApiResult

class StoryRepository(
    private val storyApi: StoryApi
) {

    private val hardcodedUserId = "1"

    suspend fun getInterestingTopStories(): ApiResult<List<Story>, ApiErrorDto> =
        storyApi.getInterestingTopStories(hardcodedUserId)
            .mapValue { it.stories }

}
