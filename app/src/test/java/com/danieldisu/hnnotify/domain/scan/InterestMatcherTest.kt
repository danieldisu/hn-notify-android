package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.domain.scan.TestData.interests
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InterestMatcherTest {

  private val interestMatcher = InterestMatcher()

  @Test
  fun `should return true when a word is present`() {
    interestMatcher.build(interests)

    assertTrue(interestMatcher.match(" java is ok ish..."))
  }

  @Test
  fun `should not return true when a word is part of a longer word in the text`() {
    interestMatcher.build(interests)

    assertFalse(interestMatcher.match("javascript is the worst language"))
  }

}

private object TestData {
  val interests = listOf(
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

}
