package com.plcoding.cryptotracker.crypto.domain.models

import java.time.LocalDateTime

data class CoinHistory(
    val marketCaps: List<Pair<String, Double>>,
    val prices: List<Pair<String, Double>>,
    val totalVolumes: List<Pair<String, Double>>
)