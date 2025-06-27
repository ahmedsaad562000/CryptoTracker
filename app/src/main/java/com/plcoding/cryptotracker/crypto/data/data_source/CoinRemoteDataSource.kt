package com.plcoding.cryptotracker.crypto.data.data_source

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin

interface CoinRemoteDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}