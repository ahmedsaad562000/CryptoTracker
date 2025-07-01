package com.plcoding.cryptotracker.crypto.domain.models

data class CoinHistory(
    val marketCaps: List<CoinPrice>,
    val prices: List<CoinPrice>,
    val totalVolumes: List<CoinPrice>
)