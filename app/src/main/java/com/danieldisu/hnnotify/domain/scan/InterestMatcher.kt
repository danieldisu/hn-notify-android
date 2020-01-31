package com.danieldisu.hnnotify.domain.scan

import com.danieldisu.hnnotify.data.interests.entities.Interest

class InterestMatcher {

  private var computedRegex: Regex? = null
  private var interests: List<Interest> = emptyList()

  fun build(interests: List<Interest>) {
    this.interests = interests
    this.computedRegex = buildRegexFor(interests.flatMap { it.keywords })
  }

  fun match(text: String): Boolean {
    val regex = computedRegex ?: throw IllegalStateException("computed regex can't be null")
    return regex.containsMatchIn(text)
  }

  fun matches(text: String): List<Interest> {
    val regex = computedRegex ?: throw IllegalStateException("computed regex can't be null")
    val matches = regex.findAll(text)
    return findInterestsWithTheMatchingString(matches)
  }

  private fun findInterestsWithTheMatchingString(matches: Sequence<MatchResult>): List<Interest> {
    return matches.map { matchResult ->
      val matchingKeyword = matchResult.value.trim()
      interests.filter { interest ->
        interest.containsKeyword(matchingKeyword)
      }
    }.flatten().toList()
  }

  private fun Interest.containsKeyword(matchingKeyword: String): Boolean {
    return keywords.any { keyword ->
      keyword.equals(matchingKeyword, true)
    }
  }

  private fun buildRegexFor(keywords: List<String>): Regex {
    val keywordRegex = keywords.joinToString("|") { "(\\b$it\\b)" }

    return "\\b$keywordRegex\\b".toRegex(RegexOption.IGNORE_CASE)
  }

}
