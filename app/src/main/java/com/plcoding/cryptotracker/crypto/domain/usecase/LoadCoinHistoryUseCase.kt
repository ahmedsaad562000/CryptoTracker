package com.plcoding.cryptotracker.crypto.domain.usecase

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class LoadCoinHistoryUseCase(private val coinRepo: CoinRepo) {

    suspend operator fun invoke(coinId: String): Result<CoinHistory, NetworkError> =
        coinRepo.getCoinMarketChart(coinId)
}