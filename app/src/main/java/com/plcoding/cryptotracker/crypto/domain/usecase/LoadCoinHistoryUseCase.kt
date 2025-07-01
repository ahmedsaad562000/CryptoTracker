package com.plcoding.cryptotracker.crypto.domain.usecase

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.core.domain.util.Result.Success
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class LoadCoinHistoryUseCase(private val coinRepo: CoinRepo) {

    suspend operator fun invoke(coinId: String): Result<CoinHistory, NetworkError> {
        return when (val result = coinRepo.getCoinMarketChart(coinId)) {
            is Success -> {
                val prices = result.data.prices
                val n = prices.size
                val step = if (n >= 24) n / 24 else 1
                val pricesFiltered = prices
                    .filterIndexed { index, _ -> (n-index) % step == 0 }
                    .take(24)

                Success(
                    CoinHistory(
                        prices = pricesFiltered,
                        marketCaps = result.data.marketCaps,
                        totalVolumes = result.data.totalVolumes
                    )
                )
            }


            is Result.Error -> {
                result
            }
        }

    }
}