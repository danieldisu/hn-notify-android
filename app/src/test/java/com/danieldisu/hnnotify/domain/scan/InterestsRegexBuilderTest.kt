package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest
import org.junit.Assert.assertEquals
import org.junit.Test

class InterestsRegexBuilderTest {

  private val interests = listOf(
    Interest(
      id = "1",
      insertedAt = System.currentTimeMillis(),
      name = "jvm",
      keywords = listOf(
        "kotlin",
        "java",
        "jvm"
      )
    ),
    Interest(
      id = "2",
      insertedAt = System.currentTimeMillis(),
      name = "Spain",
      keywords = listOf(
        "Spain",
        "Madrid",
        "Barcelona"
      )
    ),
    Interest(
      id = "3",
      insertedAt = System.currentTimeMillis(),
      name = "android",
      keywords = listOf(
        "android"
      )
    )
  )

  private val interestsRegexBuilder = InterestsRegexBuilder()

  @Test
  fun `should return a regex for each interest`() {
    val result = interestsRegexBuilder.build(interests)

    println(result.map { it.value })
    assertEquals(3, result.size)
  }
}
