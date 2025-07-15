package com.plcoding.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    @SerialName("market_caps") val marketCaps: List<List<Double>>?,
    @SerialName("prices") val prices: List<List<Double>>?,
    @SerialName("total_volumes") val totalVolumes: List<List<Double>>?
)