package com.plcoding.cryptotracker.crypto.data.remote.data_source

import com.plcoding.cryptotracker.core.data.networking.constructUrl
import com.plcoding.cryptotracker.core.data.networking.NetworkError
import com.plcoding.cryptotracker.core.data.networking.safeNetworkCall
import com.plcoding.cryptotracker.core.data.util.Result
import com.plcoding.cryptotracker.core.data.util.map
import com.plcoding.cryptotracker.crypto.data.remote.dto.CoinDto
import com.plcoding.cryptotracker.crypto.data.remote.dto.CoinHistoryDto
import com.plcoding.cryptotracker.crypto.data.mapper.toDomain
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.domain.models.CoinHistory
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter


class CoinRemoteDataSourceImpl(private val httpClient: HttpClient) : CoinRemoteDataSource {


    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeNetworkCall<List<CoinDto>> {
            httpClient.get(
                urlString = constructUrl("/markets")
            )
            {
                parameter(key = "vs_currency", value = "usd")
            }
        }.map { response ->
            response.map { coinDto ->
                coinDto.toDomain()
            }
        }
    }

    override suspend fun getCoinMarketChart(coinId: String): Result<CoinHistory, NetworkError> {

        return safeNetworkCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/${coinId}/market_chart")
            )
            {
                parameter(key = "vs_currency", value = "usd")
                parameter(key = "days", value = "1")
            }
        }.map { response ->
            response.toDomain()
        }
    }

}