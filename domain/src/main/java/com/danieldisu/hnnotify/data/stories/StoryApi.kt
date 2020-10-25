package com.danieldisu.hnnotify.data.stories

import com.danieldisu.hnnotify.data.entities.GetInterestingTopStoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryApi {

    @GET("user/{userId}/stories")
    suspend fun getInterestingTopStories(@Path("userId") userId: String): GetInterestingTopStoriesResponse

}
