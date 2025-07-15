package com.plcoding.cryptotracker.crypto.domain.repo

import com.plcoding.cryptotracker.core.data.util.Error
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory

interface CoinRepo {
    suspend fun getCoins(): Result<List<Coin>, Error>
    suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, Error>

}