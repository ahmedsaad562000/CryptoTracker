package com.plcoding.cryptotracker.core.presentation.util

import java.text.NumberFormat
import java.util.Locale

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

public fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}