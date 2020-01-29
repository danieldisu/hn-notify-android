package com.danieldisu.hnnotify.domain.fetch

import com.danieldisu.hnnotify.data.stories.StoryRepository
import com.danieldisu.hnnotify.data.stories.entities.Story
import com.danieldisu.hnnotify.data.top.TopStoriesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

private const val MAX_NUMBER_OF_STORIES_TO_FETCH = 200

class FetchTopStoriesUseCase(
  private val topStoriesRepository: TopStoriesRepository,
  private val storyRepository: StoryRepository
) {

  suspend operator fun invoke(): List<Story> {

    val topStoriesIds = topStoriesRepository.get()
      .take(MAX_NUMBER_OF_STORIES_TO_FETCH)

    return topStoriesIds.parallelMap {
      storyRepository.getById(it)
    }.filterNotNull()
  }

  private suspend fun <A, B> Iterable<A>.parallelMap(f: suspend (A) -> B): List<B> =
    coroutineScope {
      map { async { f(it) } }.awaitAll()
    }

}
