package com.sean.permitly.data.util

import com.sean.permitly.domain.error.ErrorMapper
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.error.RootError
import kotlinx.coroutines.CancellationException

suspend inline fun <T, E : RootError> safeCall(
    errorMapper: ErrorMapper<E>,
    crossinline action: suspend () -> T
): Result<T, E> {
    return try {
        Result.Success(action())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.Error(errorMapper.map(e))
    }
}