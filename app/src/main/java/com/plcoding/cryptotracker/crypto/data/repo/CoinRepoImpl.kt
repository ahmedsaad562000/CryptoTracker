package com.plcoding.cryptotracker.crypto.data.repo

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.data.data_source.CoinRemoteDataSource
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class CoinRepoImpl(private val coinRemoteDataSource: CoinRemoteDataSource) : CoinRepo {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return coinRemoteDataSource.getCoins()
    }

    override suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, NetworkError> {
        return coinRemoteDataSource.getCoinMarketChart(coinId = coinId)
    }

}