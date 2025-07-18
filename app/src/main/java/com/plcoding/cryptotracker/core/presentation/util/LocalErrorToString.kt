package com.plcoding.cryptotracker.core.presentation.util

import android.content.Context
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.data.local.LocalError
import com.plcoding.cryptotracker.core.data.networking.NetworkError
import com.plcoding.cryptotracker.core.data.util.Error

fun Error.toString(context: Context): String {
    return when (this) {
        is NetworkError -> this.toString(context)
        is LocalError -> this.toString(context)
        else -> context.getString(R.string.unknown_error)
    }
}

fun LocalError.toString(context: Context): String {
    val resId = when (this) {
        LocalError.SQLITE_ERROR -> R.string.sqlite_error
        LocalError.UNKNOWN -> R.string.unknown_error
    }
    return context.getString(resId)
}