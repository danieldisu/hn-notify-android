package com.danieldisu.hnnotify.data.stories.api

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.stories.api.GetInterestingTopStoriesResponse
import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApi {

    @GET("user/{userId}/stories")
    suspend fun getInterestingTopStories(
        @Path("userId") userId: String
    ): ApiResult<GetInterestingTopStoriesResponse, ApiErrorDto>

}
