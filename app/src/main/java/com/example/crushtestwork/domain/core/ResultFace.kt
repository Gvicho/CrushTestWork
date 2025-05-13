package com.example.crushtestwork.domain.core

import com.example.crushtestwork.domain.core.errors.Error

sealed interface ResultFace<out D, out E: Error> {
    data class Success<out D>(val data: D): ResultFace<D, Nothing>
    data class Fail<out E: Error>(val error: E): ResultFace<Nothing, E>
}

inline fun <T, E: Error, R> ResultFace<T, E>.map(map: (T) -> R): ResultFace<R, E> {
    return when(this) {
        is ResultFace.Fail -> ResultFace.Fail(error)
        is ResultFace.Success -> ResultFace.Success(map(data))
    }
}

inline fun <T, E: Error> ResultFace<T, E>.onSuccess(action: (T) -> Unit): ResultFace<T, E> {
    return when(this) {
        is ResultFace.Fail -> this
        is ResultFace.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> ResultFace<T, E>.onFail(action: (E) -> Unit): ResultFace<T, E> {
    return when(this) {
        is ResultFace.Fail -> {
            action(error)
            this
        }
        is ResultFace.Success -> this
    }
}

typealias EmptyResult<E> = ResultFace<Unit, E>