package com.plcoding.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.core.presentation.util.DisplayableNumber
import com.plcoding.cryptotracker.core.presentation.util.getDrawableIdForCoin
import com.plcoding.cryptotracker.core.presentation.util.toDisplayableNumber
import com.plcoding.cryptotracker.crypto.domain.models.Coin

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val princeUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    val coinPriceHistory: List<DataPoint>? = null,
    val lastUpdated: Long,
    @DrawableRes val iconRes: Int
)

fun Coin.toCoinUi() = CoinUi(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    princeUsd = princeUsd.toDisplayableNumber(),
    marketCapUsd = marketCapUsd.toDisplayableNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol),
    lastUpdated = lastUpdated,
)


