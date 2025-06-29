package com.plcoding.cryptotracker.crypto.data.mappers

import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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

fun getDateTime(timestamp: Double): String {
    val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
    val netDate = Date(timestamp.toLong())
    return sdf.format(netDate)
}

fun CoinHistoryDto.toCoinHistory(): CoinHistory {

    return CoinHistory(
        prices = prices?.map { it -> Pair(first = getDateTime(it[0]), second = it[1]) }
            ?: emptyList(),
        marketCaps = marketCaps?.map { it -> Pair(first = getDateTime(it[0]), second = it[1]) }
            ?: emptyList(),
        totalVolumes = totalVolumes?.map { it -> Pair(first = getDateTime(it[0]), second = it[1]) }
            ?: emptyList())
}