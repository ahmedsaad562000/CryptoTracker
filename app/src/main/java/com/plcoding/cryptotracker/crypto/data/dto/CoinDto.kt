package com.plcoding.cryptotracker.crypto.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val marketCapUsd: Double,
    val princeUsd: Double,
    val changePercent24Hr: Double
)
