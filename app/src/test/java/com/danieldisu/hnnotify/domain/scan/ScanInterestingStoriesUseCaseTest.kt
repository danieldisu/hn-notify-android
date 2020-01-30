package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.impl.InMemoryInterestsRepository
import com.danieldisu.hnnotify.domain.fetch.FetchTopStoriesUseCase
import com.danieldisu.hnnotify.testutils.builders.StoryBuilder.story
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ScanInterestingStoriesUseCaseTest {

  private val fetchTopStoriesUseCase: FetchTopStoriesUseCase = mockk()

  private val scanInterestingStoriesUseCase =
    ScanInterestingStoriesUseCase(fetchTopStoriesUseCase, InMemoryInterestsRepository(), InterestsRegexBuilder())

  @Test
  fun `should not return true when javascript is in the title`() {
    val stories = listOf(
      story(title = "Javascript is the worst lol"),
      story(title = "Java is the best lol"),
      story(title = "just kidding java is barely ok")
    )
    coEvery { fetchTopStoriesUseCase.invoke() }.answers { stories }

    val result = runBlocking { scanInterestingStoriesUseCase.invoke() }

    assertEquals(2, result.size)
  }
}
