package com.plcoding.cryptotracker.crypto.data.mapper

import com.plcoding.cryptotracker.core.data.util.getDateTime
import com.plcoding.cryptotracker.core.data.util.isoToMillis
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinEntity
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinHistoryEntity
import com.plcoding.cryptotracker.crypto.data.remote.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.remote.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.models.CoinPrice
import java.time.Instant


fun CoinDto.toDomain(): Coin {
    return Coin(
        id = id ?: "",
        rank = marketCapRank ?: 0,
        name = name ?: "",
        symbol = symbol ?: "",
        princeUsd = currentPrice ?: 0.0,
        marketCapUsd = marketCap ?: 0.0,
        changePercent24Hr = priceChangePercentage24h ?: 0.0,
        lastUpdated = isoToMillis(lastUpdated ?: "2000-07-14T14:53:04.698Z")
    )
}

fun CoinEntity.toDomain(): Coin {
    return Coin(
        id = id,
        rank = rank ?: 0,
        name = name ?: "",
        symbol = symbol ?: "",
        princeUsd = currentPrice ?: 0.0,
        marketCapUsd = marketCap ?: 0.0,
        changePercent24Hr = priceChangePercentage24h ?: 0.0,
        lastUpdated = lastUpdated
    )
}


fun CoinHistoryDto.toDomain(): CoinHistory {

    return CoinHistory(
        coinId = "",
        lastUpdated = Instant.now().toEpochMilli(),
        prices = prices?.map { it -> CoinPrice(dateTime = getDateTime(it[0]), priceUsd = it[1]) }
            ?: emptyList(),
    )
}


fun List<CoinHistoryEntity>.toDomain(): CoinHistory {
    return CoinHistory(
        coinId = if (this.isEmpty()) "" else this[0].id,
        lastUpdated = if (this.isEmpty()) 0L else this[0].lastUpdated,
        prices = if (this.isEmpty()) emptyList() else this.map { it ->
            CoinPrice(
                dateTime = getDateTime(it.dateTime),
                priceUsd = it.price
            )
        }

    )
}

fun Coin.toEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        currentPrice = princeUsd,
        marketCap = marketCapUsd,
        priceChangePercentage24h = changePercent24Hr,
        lastUpdated = lastUpdated
    )
}

fun CoinHistory.toEntity(): List<CoinHistoryEntity> {
    return this.prices.map { it ->
        CoinHistoryEntity(
            id = coinId,
            dateTime = it.dateTime.time.toDouble(),
            price = it.priceUsd,
            lastUpdated = lastUpdated
        )
    }
}