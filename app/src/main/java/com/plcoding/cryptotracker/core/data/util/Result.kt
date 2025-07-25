package com.plcoding.cryptotracker.core.data.util

typealias DomainError = Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
    data class Fallback<out D, out E : DomainError>(val data: D, val error: E) : Result<D, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
        is Result.Fallback -> Result.Fallback(map(data), error)
    }
}

fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }

        is Result.Fallback -> {
            action(data)
            this
        }

        is Result.Error -> this


    }
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Fallback -> {
            action(error)
            this
        }

        is Result.Success -> this

    }
}

typealias EmptyResult<E> = Result<Unit, E>