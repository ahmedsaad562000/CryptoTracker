package com.plcoding.cryptotracker.crypto.data.data_source

import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.data.networking.safeCall
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.mappers.toCoin
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class CoinRemoteDataSourceImpl(private val httpClient: HttpClient) : CoinRemoteDataSource {


    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<List<CoinDto>> {
            httpClient.get(
                urlString = constructUrl("/markets")
            )
            {
                parameter(key = "vs_currency", value = "usd")
            }
        }.map { response ->
            response.map { coinDto ->
                coinDto.toCoin()
            }
        }
    }

}