package com.danieldisu.hnnotify

import com.danieldisu.hnnotify.FetchTopStoriesUseCaseIntegrationTest.Dependencies.storyRepository
import com.danieldisu.hnnotify.FetchTopStoriesUseCaseIntegrationTest.Dependencies.topStoriesRepository
import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.impl.StoryRepositoryImpl
import com.danieldisu.hnnotify.data.stories.impl.StoryService
import com.danieldisu.hnnotify.data.top.TopStoriesRepository
import com.danieldisu.hnnotify.data.top.impl.TopStoriesRepositoryImpl
import com.danieldisu.hnnotify.data.top.impl.TopStoriesService
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.infrastructure.network.OkHttpClientBuilder
import com.danieldisu.hnnotify.infrastructure.network.RetrofitHNServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

class FetchTopStoriesUseCaseIntegrationTest {

  private val fetchNewStoriesUseCase = FetchTopStoriesUseCase(topStoriesRepository, storyRepository)

  @Test
  fun name() {
    GlobalScope.launch(Dispatchers.Unconfined) {
      val result = fetchNewStoriesUseCase()
      result.forEach { println(it) }
    }

    Thread.sleep(1500)
  }

  object Dependencies {
    private val retrofitHNServiceBuilder = RetrofitHNServiceBuilder(OkHttpClientBuilder())
    private val storyService = retrofitHNServiceBuilder.build(StoryService::class.java)
    private val topStoriesService = retrofitHNServiceBuilder.build(TopStoriesService::class.java)
    val storyRepository: StoryRepository = StoryRepositoryImpl(storyService)
    val topStoriesRepository: TopStoriesRepository = TopStoriesRepositoryImpl(topStoriesService)
  }
}
