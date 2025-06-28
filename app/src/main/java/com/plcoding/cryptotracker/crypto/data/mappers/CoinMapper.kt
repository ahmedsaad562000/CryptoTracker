package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.domain.models.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id ?: "",
        rank = marketCapRank ?: 0,
        name = name ?: "",
        symbol = symbol ?: "",
        princeUsd = currentPrice ?: 0.0,
        marketCapUsd = marketCap ?: 0.0,
        changePercent24Hr = priceChangePercentage24h ?: 0.0
    )
}