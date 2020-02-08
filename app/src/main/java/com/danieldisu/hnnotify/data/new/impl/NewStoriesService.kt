package com.danieldisu.hnnotify.data.new.impl

import retrofit2.http.GET

interface NewStoriesService {

  @GET("newstories.json")
  suspend fun get(): List<String>

}
