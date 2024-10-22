package com.danieldisu.hnnotify.data.interests.api

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.slack.eithernet.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface InterestApi {

    @GET("user/{userId}/interest")
    suspend fun getUserInterests(
        @Path("userId") userId: String
    ): ApiResult<GetUserInterestsApiResponse, ApiErrorDto>


    @PUT("user/{userId}/interest")
    suspend fun createInterest(
        @Path("userId") userId: String,
        @Body interestRequest: HttpInterest,
    ): ApiResult<Unit, ApiErrorDto>

}
