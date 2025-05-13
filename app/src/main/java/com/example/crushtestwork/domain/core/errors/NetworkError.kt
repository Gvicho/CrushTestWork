package com.example.crushtestwork.domain.core.errors

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    BAD_REQUEST,
    UNKNOWN;
}

fun getNetworkError(
    statusCode: Int
) : NetworkError {
    return when (statusCode) {
        400 -> NetworkError.BAD_REQUEST
        401 -> NetworkError.UNAUTHORIZED
        409 -> NetworkError.CONFLICT
        408 -> NetworkError.REQUEST_TIMEOUT
        413 -> NetworkError.PAYLOAD_TOO_LARGE
        in 500..599 -> { // this is not clients fault
            NetworkError.SERVER_ERROR
        }
        1000 -> NetworkError.NO_INTERNET
        1100 -> NetworkError.SERIALIZATION
        else -> NetworkError.UNKNOWN
    }
}