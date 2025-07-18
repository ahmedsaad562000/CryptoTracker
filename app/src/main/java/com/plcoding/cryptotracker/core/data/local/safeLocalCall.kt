package com.plcoding.cryptotracker.core.data.local

import androidx.sqlite.SQLiteException
import com.plcoding.cryptotracker.core.data.util.Result
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeLocalCall(
    execute: suspend () -> T
): Result<T, LocalError> {
    val response = try {
        execute()
    } catch (e: SQLiteException) {
        return Result.Error(LocalError.SQLITE_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(LocalError.UNKNOWN)
    }

    return Result.Success(response)
}