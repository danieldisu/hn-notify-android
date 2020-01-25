package com.danieldisu.hnnotify.data.stories.impl

import com.danieldisu.hnnotify.data.stories.entities.Story
import retrofit2.http.GET
import retrofit2.http.Path

interface StoryService {

  @GET("item/{itemId}.json")
  suspend fun getById(@Path("itemId") itemId: String): Story

}
