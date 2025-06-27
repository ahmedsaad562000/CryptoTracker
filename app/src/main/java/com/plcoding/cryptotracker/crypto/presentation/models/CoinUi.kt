package com.plcoding.cryptotracker.crypto.presentation.models

import androidx.annotation.DrawableRes
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.util.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale // ✅ Add this

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val princeUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int
)
data class DisplayableNumber(
    val value: Double,
    val formatted: String
)


fun Coin.toCoinUi() = CoinUi(
    id = id,
    rank = rank,
    name = name,
    symbol = symbol,
    princeUsd = princeUsd.toDisplayableNumber(),
    marketCapUsd = marketCapUsd.toDisplayableNumber(),
    changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
    iconRes = getDrawableIdForCoin(symbol)
    )

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        maximumFractionDigits = 2
    }

    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}
