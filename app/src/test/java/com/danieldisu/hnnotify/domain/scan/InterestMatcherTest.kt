package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest
import com.danieldisu.hnnotify.domain.scan.TestData.duplicatedJavaInterest
import com.danieldisu.hnnotify.domain.scan.TestData.interests
import com.danieldisu.hnnotify.domain.scan.TestData.jvmInterest
import org.junit.Assert.*
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

  @Test
  fun `should return the list of interests that matches the title`() {
    interestMatcher.build(interests)

    val interestThatMatches = interestMatcher.matches(" java is ok ish...")
    assertEquals(listOf(jvmInterest), interestThatMatches)
  }

  @Test
  fun `should return not only the first interest that matches`() {
    interestMatcher.build(interests.plus(duplicatedJavaInterest))

    val interestThatMatches = interestMatcher.matches(" java is ok ish...")
    assertEquals(listOf(jvmInterest, duplicatedJavaInterest), interestThatMatches)
  }
}

private object TestData {
  val jvmInterest = Interest(
    id = "1",
    insertedAt = System.currentTimeMillis(),
    name = "jvm",
    keywords = listOf(
      "kotlin",
      "java",
      "jvm"
    )
  )

  val interests = listOf(
    jvmInterest,
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

  val duplicatedJavaInterest = Interest(
    id = "4",
    insertedAt = System.currentTimeMillis(),
    name = "javaworld",
    keywords = listOf(
      "java"
    )
  )

}
