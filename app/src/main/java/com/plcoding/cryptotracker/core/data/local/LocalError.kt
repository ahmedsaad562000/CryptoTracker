package com.plcoding.cryptotracker.core.data.local

import com.plcoding.cryptotracker.core.data.util.Error

enum class LocalError : Error {
    SQLITE_ERROR,
    UNKNOWN
}