package com.plcoding.cryptotracker.crypto.domain.models

data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val princeUsd: Double,
    val changePercent24Hr: Double
)