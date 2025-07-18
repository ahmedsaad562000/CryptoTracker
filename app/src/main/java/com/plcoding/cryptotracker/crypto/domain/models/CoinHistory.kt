package com.plcoding.cryptotracker.crypto.domain.models

data class CoinHistory(
    val coinId: String,
    val prices: List<CoinPrice>,
    val lastUpdated : Long
)