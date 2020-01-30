package com.danieldisu.hnnotify.data.top.impl

import retrofit2.http.GET

interface TopStoriesService {

  @GET("topstories.json")
  suspend fun get(): List<String>

}
