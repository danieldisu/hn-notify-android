package com.danieldisu.hnnotify.data.interests

import com.slack.eithernet.ApiResult
import java.io.IOException

typealias InterestOrError = ApiResult<Interest, ApiResult.Failure<Throwable>>

class InterestRepository {

    suspend fun saveInterest(interest: RecentlyCreatedKeywordInterest): InterestOrError {
        return ApiResult.networkFailure(IOException())
    }

    suspend fun getInterests(): List<Interest> {
        return emptyList()
    }

    suspend fun updateInterest(interest: Interest) {

    }

}
