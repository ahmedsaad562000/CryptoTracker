package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.core.data.networking.HttpClientFactory
import com.plcoding.cryptotracker.crypto.data.remote.data_source.CoinRemoteDataSource
import com.plcoding.cryptotracker.crypto.data.remote.data_source.CoinRemoteDataSourceImpl

import io.ktor.client.engine.cio.CIO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// the Koin module that contains all the dependencies required by the application
val remoteModule = module {

    // provide a single instance of HttpClient created with CIO engine
    single { HttpClientFactory.create(CIO.create()) }

    // provide a single instance of CoinRemoteDataSourceImpl which implements CoinRemoteDataSource
    singleOf(constructor = ::CoinRemoteDataSourceImpl).bind<CoinRemoteDataSource>()

}