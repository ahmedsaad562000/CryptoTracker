package com.plcoding.cryptotracker.crypto.data.local.data_source

import com.plcoding.cryptotracker.core.data.local.LocalError
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinHistoryEntity
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory

interface CoinLocalDataSource {
    suspend fun getCoins(): Result<List<Coin>, LocalError>
    suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, LocalError>

    suspend fun saveCoins(coins: List<Coin>): Result<Unit, LocalError>
    suspend fun saveCoinHistory(coinsHistory: CoinHistory): Result<Unit, LocalError>

}