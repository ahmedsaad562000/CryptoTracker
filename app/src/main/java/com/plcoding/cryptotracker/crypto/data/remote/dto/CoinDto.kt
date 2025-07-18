package com.plcoding.cryptotracker.crypto.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinDto(
    @SerialName("ath_change_percentage") val athChangePercentage: Double?,
    @SerialName("ath_date") val athDate: String?,
    @SerialName("atl") val atlPrice: Double?, // Make sure this key exists — it's not in your sample JSON!
    @SerialName("atl_change_percentage") val atlChangePercentage: Double?,
    @SerialName("atl_date") val atlDate: String?,
    @SerialName("circulating_supply") val circulatingSupply: Double?,
    @SerialName("current_price") val currentPrice: Double?,
    @SerialName("fully_diluted_valuation") val fullyDilutedValuation: Double?,
    @SerialName("high_24h") val high24h: Double?,
    @SerialName("id") val id: String?,
    @SerialName("image") val image: String?,
    @SerialName("last_updated") val lastUpdated: String?,
    @SerialName("low_24h") val low24h: Double?,
    @SerialName("market_cap") val marketCap: Double?,
    @SerialName("market_cap_change_24h") val marketCapChange24h: Double?,
    @SerialName("market_cap_change_percentage_24h") val marketCapChangePercentage24h: Double?,
    @SerialName("market_cap_rank") val marketCapRank: Int?,
    @SerialName("max_supply") val maxSupply: Double?,
    @SerialName("name") val name: String?,
    @SerialName("price_change_24h") val priceChange24h: Double?,
    @SerialName("price_change_percentage_24h") val priceChangePercentage24h: Double?,
    @SerialName("roi") val roi: Roi?,
    @SerialName("symbol") val symbol: String?,
    @SerialName("total_supply") val totalSupply: Double?,
    @SerialName("total_volume") val totalVolume: Double?
)
