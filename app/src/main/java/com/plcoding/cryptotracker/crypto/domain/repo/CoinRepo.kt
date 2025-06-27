package com.plcoding.cryptotracker.crypto.domain.repo

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.models.Coin

interface CoinRepo {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}