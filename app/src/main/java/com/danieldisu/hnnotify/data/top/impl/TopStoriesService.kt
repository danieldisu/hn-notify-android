package com.danieldisu.hnnotify.data.top.impl

import retrofit2.http.GET

interface TopStoriesService {

  @GET("newstories.json")
  suspend fun get(): List<String>

}
