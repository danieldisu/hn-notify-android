package com.danieldisu.hnnotify.data.stories.entities

import com.danieldisu.hnnotify.data.common.StoryId
import kotlinx.serialization.Serializable
import java.net.URL
import java.util.Locale

@Serializable
data class Story(
  private val id: String,
  val kids: List<String> = emptyList(),
  val by: String,
  val score: Int,
  val time: Long,
  val title: String,
  val type: String,
  val url: String? = null,
  val text: String? = null
) {

  val storyId: StoryId = StoryId(id)
  val lowerCaseTitle = title.toLowerCase(Locale.ENGLISH)

  fun commentCount(): Int = kids.size

  fun getDomain(): String? {
    if (url == null) return null
    val host = URL(url).host
    return if (host.startsWith("www")) host.substring("www".length + 1)
    else host
  }

}
