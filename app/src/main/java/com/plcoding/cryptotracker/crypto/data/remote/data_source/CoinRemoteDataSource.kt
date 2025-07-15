package com.plcoding.cryptotracker.crypto.data.remote.data_source

import com.plcoding.cryptotracker.core.data.networking.NetworkError
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory

interface CoinRemoteDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, NetworkError>
}