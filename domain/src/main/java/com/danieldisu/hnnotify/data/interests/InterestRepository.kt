package com.danieldisu.hnnotify.data.interests

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.danieldisu.hnnotify.data.core.mapValue
import com.danieldisu.hnnotify.data.interests.api.HttpInterest
import com.danieldisu.hnnotify.data.interests.api.InterestApi
import com.slack.eithernet.ApiResult

typealias InterestOrError = ApiResult<Interest, ApiErrorDto>

class InterestRepository(
    private val interestApi: InterestApi
) {

    suspend fun saveInterest(userId: String, name: String, keywords: List<String>): ApiResult<Unit, ApiErrorDto> {
        return interestApi.createInterest(userId, HttpInterest(name, keywords))
    }

    suspend fun getInterests(userId: String): ApiResult<List<Interest>, ApiErrorDto> =
        interestApi.getUserInterests(userId)
            .mapValue { it.map { httpInterest -> toDomainInterest(httpInterest) } }

    suspend fun updateInterest(interest: RecentlyUpdatedInterest): InterestOrError {
        interest as RecentlyUpdatedKeywordInterest
        return ApiResult.Success(Interest(interest.id, interest.name ?: "", interest.keywords))
    }

    suspend fun getInterest(userId: String, interestId: String): InterestOrError {
        return when (val result = getInterests(userId)) {
            is ApiResult.Success -> {
                val interest = result.response.find { it.id == interestId }
                if (interest == null) {
                    ApiResult.apiFailure(ApiErrorDto)
                } else {
                    ApiResult.Success(interest)
                }
            }
            is ApiResult.Failure -> result
        }
    }

}

private fun toDomainInterest(httpInterest: HttpInterest) =
    Interest(httpInterest.interestName, httpInterest.interestName, httpInterest.interestKeywords)
