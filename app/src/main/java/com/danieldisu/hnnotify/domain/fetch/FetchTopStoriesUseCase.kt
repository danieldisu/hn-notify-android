package com.danieldisu.hnnotify.domain.fetch

import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.data.top.TopStoriesRepository
import com.danieldisu.hnnotify.infrastructure.logging.LOG
import com.danieldisu.hnnotify.infrastructure.logging.TRACE
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

private const val MAX_NUMBER_OF_STORIES_TO_FETCH = 400

class FetchTopStoriesUseCase(
  private val topStoriesRepository: TopStoriesRepository,
  private val storyRepository: StoryRepository
) {

  suspend operator fun invoke(): List<Story> {
    LOG("Fetching Top Stories")
    TRACE("FetchTopStoriesUseCase::init")

    val topStoriesIds = topStoriesRepository.get()
      .take(MAX_NUMBER_OF_STORIES_TO_FETCH)

    TRACE("FetchTopStoriesUseCase::fetched ${topStoriesIds.size} topstories")

    return topStoriesIds.parallelMap {
      storyRepository.getById(it)
    }.filterNotNull()
      .apply { TRACE("FetchTopStoriesUseCase::finished") }
  }

  private suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> =
    coroutineScope {
      map { async { f(it) } }.awaitAll()
    }

}
