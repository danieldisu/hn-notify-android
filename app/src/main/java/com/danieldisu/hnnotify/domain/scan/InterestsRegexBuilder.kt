package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest

class InterestsRegexBuilder {

  fun build(interests: List<Interest>): Map<Interest, Trie> {
    return interests
      .map { it to buildRegexForInterest(it) }
      .toMap()
  }

  private fun buildRegexForInterest(interest: Interest): Trie {
    val trie = Trie()

    interest.keywords.forEach(trie::insert)

    return trie

//    val keywords = interest
//      .keywords
//      .map { it.toLowerCase(Locale.ROOT) }
//      .joinToString("|") { "(\\w*${it}*)" }
//
//    return "\\b$keywords\\b".toRegex()
  }

}
