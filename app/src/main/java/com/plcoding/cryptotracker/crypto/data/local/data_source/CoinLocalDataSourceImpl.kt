package com.plcoding.cryptotracker.crypto.data.local.data_source

import com.plcoding.cryptotracker.core.data.local.LocalError
import com.plcoding.cryptotracker.core.data.local.safeLocalCall
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.core.data.util.map
import com.plcoding.cryptotracker.crypto.data.local.dao.CoinDao
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinEntity
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinHistoryEntity
import com.plcoding.cryptotracker.crypto.data.mapper.toDomain
import com.plcoding.cryptotracker.crypto.data.mapper.toEntity
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory

class CoinLocalDataSourceImpl(private val coinDao: CoinDao) : CoinLocalDataSource {
    override suspend fun getCoins(): Result<List<Coin>, LocalError> {
        return safeLocalCall<List<CoinEntity>> {


            coinDao.getAllCoins()
        }.map { response ->
            response.map { coinEntity ->
                coinEntity.toDomain()
            }
        }
    }

    override suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, LocalError> {
        return safeLocalCall<List<CoinHistoryEntity>> {

            coinDao.getCoinHistory(coinId)
        }.map { response ->
            response.toDomain()
        }
    }

    override suspend fun saveCoins(coins: List<Coin>): Result<Unit, LocalError> {

        return safeLocalCall<Unit> {
            coinDao.insertAllCoins(coins = coins.map { it.toEntity() }.toTypedArray())
        }

    }

    override suspend fun saveCoinHistory(coinsHistory: CoinHistory): Result<Unit, LocalError> {
        return safeLocalCall<Unit> {
            coinDao.deleteCoinHistory(id = coinsHistory.coinId)
            coinDao.insertCoinHistory(
                coinsHistory = coinsHistory.toEntity()
                    .toTypedArray()
            )
        }
    }

}