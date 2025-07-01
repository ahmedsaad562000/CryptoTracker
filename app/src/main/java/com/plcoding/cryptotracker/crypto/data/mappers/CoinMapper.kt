package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.models.CoinPrice
import java.util.Date

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

fun getDateTime(timestamp: Double): Date {
    val netDate = Date(timestamp.toLong())
    return netDate
}

fun CoinHistoryDto.toCoinHistory(): CoinHistory {

    return CoinHistory(
        prices = prices?.map { it -> CoinPrice(dateTime = getDateTime(it[0]), priceUsd = it[1]) }
            ?: emptyList(),
        marketCaps = marketCaps?.map { it ->
            CoinPrice(
                dateTime = getDateTime(it[0]),
                priceUsd = it[1]
            )
        }
            ?: emptyList(),
        totalVolumes = totalVolumes?.map { it ->
            CoinPrice(
                dateTime = getDateTime(it[0]),
                priceUsd = it[1]
            )
        }
            ?: emptyList(),
    )
}