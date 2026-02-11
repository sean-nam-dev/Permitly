package com.sean.permitly.domain.result

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}