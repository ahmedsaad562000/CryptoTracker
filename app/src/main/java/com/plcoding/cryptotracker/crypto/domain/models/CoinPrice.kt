package com.plcoding.cryptotracker.crypto.domain.models

import java.util.Date

data class CoinPrice(
    val priceUsd: Double,
    val dateTime: Date
)
