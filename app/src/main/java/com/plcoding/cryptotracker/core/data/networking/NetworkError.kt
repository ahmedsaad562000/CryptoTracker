package com.plcoding.cryptotracker.core.data.networking

import com.plcoding.cryptotracker.core.data.util.Error

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}