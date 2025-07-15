package com.plcoding.cryptotracker.crypto.data.repo

import android.util.Log
import com.plcoding.cryptotracker.core.data.networking.NetworkError
import com.plcoding.cryptotracker.core.data.util.Error
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.crypto.data.local.data_source.CoinLocalDataSource
import com.plcoding.cryptotracker.crypto.data.remote.data_source.CoinRemoteDataSource
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class CoinRepoImpl(
    private val coinRemoteDataSource: CoinRemoteDataSource,
    private val coinLocalDataSource: CoinLocalDataSource
) : CoinRepo {
    override suspend fun getCoins(): Result<List<Coin>, Error> {
        return when (val result = coinRemoteDataSource.getCoins()) {
            is Result.Success -> {
                if (result.data.isNotEmpty()) {
                    coinLocalDataSource.saveCoins(result.data)
                }
                result
            }

            is Result.Error -> {

                val localResult = coinLocalDataSource.getCoins()
                if (localResult is Result.Success) {
                    Result.Fallback(localResult.data, result.error)
                } else {
                    localResult
                }

            }

            is Result.Fallback -> {
                result
            }
        }
    }

    override suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, Error> {
        Log.e("CoinRepoImpl", "getCoinMarketChart")
        return when (val result = coinRemoteDataSource.getCoinMarketChart(coinId = coinId)) {
            is Result.Success -> {
                Log.e("CoinRepoImpl", "getCoinMarketChart network is not empty ${result.data}")
                if (result.data.prices.isNotEmpty()) {
                    coinLocalDataSource.saveCoinHistory(
                        result.data.copy(
                            coinId = coinId
                        )
                    )

                    result
                } else {
                    Log.e("CoinRepoImpl", "getCoinMarketChart network is empty")
                    val localResult = coinLocalDataSource.getCoinMarketChart(coinId = coinId)
                    if (localResult is Result.Success) {
                        Result.Fallback(localResult.data, NetworkError.UNKNOWN)
                    } else {
                        localResult
                    }
                }

            }

            is Result.Error -> {

                val localResult = coinLocalDataSource.getCoinMarketChart(coinId = coinId)
                if (localResult is Result.Success) {
                    Result.Fallback(localResult.data, result.error)
                } else {
                    localResult
                }
            }


            is Result.Fallback -> result
        }
    }
}