package com.danieldisu.hnnotify.data.core

import android.util.Log
import com.slack.eithernet.ApiResult
import kotlinx.serialization.Serializable

fun <T : Any, R : Any, E> ApiResult<T, E>.mapValue(mappingFunction: (T) -> R): ApiResult<R, E> =
    when (this) {
        is ApiResult.Success -> ApiResult.Success(mappingFunction(response))
        is ApiResult.Failure.NetworkFailure -> ApiResult.networkFailure(error)
        is ApiResult.Failure.UnknownFailure -> ApiResult.unknownFailure(error)
        is ApiResult.Failure.HttpFailure -> ApiResult.httpFailure(code, error)
        is ApiResult.Failure.ApiFailure -> ApiResult.apiFailure(error)
    }

fun <E> ApiResult.Failure<E>.getError(): ApiError =
    when (this) {
        is ApiResult.Failure.NetworkFailure -> ApiError(error)
        is ApiResult.Failure.UnknownFailure -> ApiError(error)
        is ApiResult.Failure.HttpFailure -> ApiError(UnknownError())
        is ApiResult.Failure.ApiFailure -> ApiError(UnknownError())
    }

fun <T, E> ApiResult<T, E>.logErrors(): ApiResult<T, E> =
    this.also {
        if (it is ApiResult.Failure) {
            Log.e(null, null, it.getError().cause)
        }
    }

@Serializable
class ApiErrorDto
