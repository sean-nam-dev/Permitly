package com.sean.permitly.domain.error

inline fun <D, E : RootError> Result<D, E>.onSuccess(
    action: (D) -> Unit
): Result<D, E> {
    if (this is Result.Success) {
        action(data)
    }
    return this
}

inline fun <D, E : RootError> Result<D, E>.onError(
    action: (E) -> Unit
): Result<D, E> {
    if (this is Result.Error) {
        action(error)
    }
    return this
}

inline fun <D, E : RootError, R> Result<D, E>.flatMap(
    transform: (D) -> Result<R, E>
): Result<R, E> = when (this) {
    is Result.Success -> transform(data)
    is Result.Error -> this
}