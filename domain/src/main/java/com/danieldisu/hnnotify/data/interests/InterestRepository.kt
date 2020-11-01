package com.danieldisu.hnnotify.data.interests

import com.slack.eithernet.ApiResult

typealias InterestOrError = ApiResult<Interest, ApiResult.Failure<Throwable>>

class InterestRepository {

    suspend fun saveInterest(interest: RecentlyCreatedKeywordInterest): InterestOrError {
        return ApiResult.Success(KeywordInterest("3", interest.name ?: "", interest.keywords))
    }

    suspend fun getInterests(): List<Interest> {
        return listOf(
            KeywordInterest(id = "1", name = "JVM", keywords = listOf("JVM", "Kotlin", "Java", "Scala")),
            KeywordInterest(
                id = "2",
                name = "Spain",
                keywords = listOf("Spain", "Spanish", "Spaniard", "Madrid", "Barcelona")
            ),
        )
    }

    suspend fun updateInterest(interest: RecentlyUpdatedInterest): InterestOrError {
        interest as RecentlyUpdatedKeywordInterest
        return ApiResult.Success(KeywordInterest(interest.id, interest.name ?: "", interest.keywords))
    }

}
