package com.danieldisu.hnnotify.data.interests.impl

import com.danieldisu.hnnotify.data.interests.InterestsRepository
import com.danieldisu.hnnotify.data.interests.entities.Interest

class InMemoryInterestsRepository: InterestsRepository {

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
    ),
    Interest(
      id = "4",
      insertedAt = System.currentTimeMillis(),
      name = "rust",
      keywords = listOf(
        "rust"
      )
    )
  )

  override fun getInterests(): List<Interest> {
    return interests
  }

}
