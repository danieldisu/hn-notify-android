package com.danieldisu.hnnotify

import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.koin.core.context.GlobalContext

class FetchTopStoriesUseCaseIntegrationTest {

  private val topStoriesUseCase = GlobalContext.get().koin.get<FetchTopStoriesUseCase>()

  @Test
  fun name() {

    GlobalScope.launch(Dispatchers.Unconfined) {
      val result = topStoriesUseCase()
      result.forEach { println(it) }
    }

    Thread.sleep(1500)
  }

}
