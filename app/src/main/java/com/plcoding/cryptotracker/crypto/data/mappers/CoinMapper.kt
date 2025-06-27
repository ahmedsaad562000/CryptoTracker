package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.domain.models.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        princeUsd = princeUsd,
        marketCapUsd = marketCapUsd,
        changePercent24Hr = changePercent24Hr
    )
}