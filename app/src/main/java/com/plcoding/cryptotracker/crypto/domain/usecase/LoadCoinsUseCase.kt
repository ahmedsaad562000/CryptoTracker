package com.plcoding.cryptotracker.crypto.domain.usecase

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo

class LoadCoinsUseCase(private val coinRepo: CoinRepo) {

    suspend operator fun invoke(): Result<List<Coin>, NetworkError> = coinRepo.getCoins()
}