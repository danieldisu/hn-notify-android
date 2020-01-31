package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest
import java.util.*

class InterestMatcher {

  private var computedInterests: Map<Interest, Regex> = emptyMap()

  fun build(interests: List<Interest>) {
    computedInterests = interests
      .map { it to buildRegexForInterest(it) }
      .toMap()
  }

  fun match(text: String): Boolean {
    return computedInterests.any { it.value.containsMatchIn(text) }
  }

  private fun buildRegexForInterest(interest: Interest): Regex {
    val keywords = interest
      .keywords
      .map { it.toLowerCase(Locale.ENGLISH) }
      .joinToString("|") { "(\\b$it\\b)" }

    return "\\b$keywords\\b".toRegex(RegexOption.IGNORE_CASE)
  }

}
