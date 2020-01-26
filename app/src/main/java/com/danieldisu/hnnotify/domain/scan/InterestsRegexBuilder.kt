package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest
import java.util.*

class InterestsRegexBuilder {

  fun build(interests: List<Interest>): Map<Interest, Regex> {
    return interests
      .map { it to buildRegexForInterest(it) }
      .toMap()
  }

  private fun buildRegexForInterest(interest: Interest): Regex {
    val keywords = interest
      .keywords
      .map { it.toLowerCase(Locale.ROOT) }
      .joinToString("|") { "(\\w*${it}*)" }

    return "\\b$keywords\\b".toRegex()
  }

}
