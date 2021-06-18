package com.danieldisu.hnnotify.domain.core

import com.danieldisu.hnnotify.data.core.ApiErrorDto
import com.slack.eithernet.ApiResult

sealed class GenericUseCaseResult<out T> {

    data class Success<T>(
        val value: T
    ) : GenericUseCaseResult<T>()

    data class Error(
        val value: Throwable
    ) : GenericUseCaseResult<Nothing>()

}

fun <T> ApiResult<T, ApiErrorDto>.toGenericUseCaseResult(): GenericUseCaseResult<T> =
    when (this) {
        is ApiResult.Success -> GenericUseCaseResult.Success(response)
        is ApiResult.Failure.NetworkFailure -> GenericUseCaseResult.Error(error)
        is ApiResult.Failure.UnknownFailure -> GenericUseCaseResult.Error(error)
        is ApiResult.Failure.HttpFailure -> GenericUseCaseResult.Error(UnknownError())
        is ApiResult.Failure.ApiFailure -> GenericUseCaseResult.Error(UnknownError())
    }
