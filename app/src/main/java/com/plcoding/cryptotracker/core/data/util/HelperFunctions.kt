package com.plcoding.cryptotracker.core.data.util


import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

public fun getDateTime(timestamp: Double): Date {
    val netDate = Date(timestamp.toLong())
    return netDate
}


public fun getLastUpdatedString(timestamp: Long): String {
    val diff = System.currentTimeMillis() - timestamp

    val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff)
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val weeks = days / 7

    return when {
        minutes < 1 -> "now"
        minutes < 60 -> "${minutes}m"
        hours < 24 -> "${hours}h"
        days < 7 -> "${days}d"
        weeks < 3 -> "${weeks}w"
        else -> {
            // Format as date after 3 weeks
            val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            sdf.format(Date(timestamp))
        }
    }
}

public fun isoToMillis(isoString: String): Long {
    return Instant.parse(isoString).toEpochMilli()
}