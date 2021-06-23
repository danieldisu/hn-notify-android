package com.danieldisu.hnnotify.data.stories

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.core.mapValue
import com.danieldisu.hnnotify.data.stories.api.StoryApi
import com.slack.eithernet.ApiResult

class StoryRepository(
    private val storyApi: StoryApi
) {

    suspend fun getInterestingTopStories(userId: String): ApiResult<List<Story>, ApiErrorDto> =
        storyApi.getInterestingTopStories(userId)
            .mapValue { it.stories }

}
