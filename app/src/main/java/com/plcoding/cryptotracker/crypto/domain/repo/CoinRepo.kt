package com.plcoding.cryptotracker.crypto.domain.repo

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory

interface CoinRepo {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, NetworkError>
}