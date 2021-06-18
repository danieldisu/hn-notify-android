package com.danieldisu.hnnotify.data.interests

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.core.mapValue
import com.danieldisu.hnnotify.data.interests.api.HttpInterest
import com.danieldisu.hnnotify.data.interests.api.InterestApi
import com.slack.eithernet.ApiResult

typealias InterestOrError = ApiResult<Interest, ApiResult.Failure<Throwable>>

class InterestRepository(
    private val interestApi: InterestApi
) {

    suspend fun saveInterest(interest: RecentlyCreatedKeywordInterest): InterestOrError {
        return ApiResult.Success(KeywordInterest("3", interest.name ?: "", interest.keywords))
    }

    suspend fun getInterests(userId: String): ApiResult<List<Interest>, ApiErrorDto> =
        interestApi.getUserInterests(userId)
            .mapValue { it.map { httpInterest -> toDomainInterest(httpInterest) } }

    suspend fun updateInterest(interest: RecentlyUpdatedInterest): InterestOrError {
        interest as RecentlyUpdatedKeywordInterest
        return ApiResult.Success(KeywordInterest(interest.id, interest.name ?: "", interest.keywords))
    }

}

private fun toDomainInterest(httpInterest: HttpInterest) =
    KeywordInterest(httpInterest.interestName, httpInterest.interestName, httpInterest.interestKeywords)
