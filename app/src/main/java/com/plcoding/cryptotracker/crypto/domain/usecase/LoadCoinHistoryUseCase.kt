package com.plcoding.cryptotracker.crypto.domain.usecase

import com.plcoding.cryptotracker.core.data.util.Error
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class LoadCoinHistoryUseCase(private val coinRepo: CoinRepo) {

    suspend operator fun invoke(coinId: String): Result<CoinHistory, Error> {
        return when (val result = coinRepo.getCoinMarketChart(coinId)) {
            is Result.Error -> {
                result
            }

            is Result.Success -> {
                val prices = result.data.prices
                val n = result.data.prices.size
                val step = if (n >= 24) n / 24 else 1
                val pricesFiltered = prices
                    .filterIndexed { index, _ -> (n - index) % step == 0 }
                    .take(24)

                val coinHistory = CoinHistory(
                    coinId = result.data.coinId,
                    prices = pricesFiltered,
                    lastUpdated = result.data.lastUpdated
                )
                Result.Success(coinHistory)
            }

            is Result.Fallback -> {
                val prices = result.data.prices
                val n = result.data.prices.size
                val step = if (n >= 24) n / 24 else 1
                val pricesFiltered = prices
                    .filterIndexed { index, _ -> (n - index) % step == 0 }
                    .take(24)

                val coinHistory = CoinHistory(
                    coinId = result.data.coinId,
                    prices = pricesFiltered,
                    lastUpdated = result.data.lastUpdated
                )
                Result.Fallback(coinHistory, result.error)
            }


        }

    }
}