package com.plcoding.cryptotracker.di

import com.plcoding.cryptotracker.core.data.networking.HttpClientFactory
import com.plcoding.cryptotracker.crypto.data.data_source.CoinRemoteDataSource
import com.plcoding.cryptotracker.crypto.data.data_source.CoinRemoteDataSourceImpl
import com.plcoding.cryptotracker.crypto.data.repo.CoinRepoImpl
import com.plcoding.cryptotracker.crypto.domain.repo.CoinRepo
import com.plcoding.cryptotracker.crypto.domain.usecase.LoadCoinHistoryUseCase
import com.plcoding.cryptotracker.crypto.domain.usecase.LoadCoinsUseCase
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_model.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// the Koin module that contains all the dependencies required by the application
val appModule = module {

    // provide a single instance of HttpClient created with CIO engine
    single { HttpClientFactory.create(CIO.create()) }

    // provide a single instance of CoinRemoteDataSourceImpl which implements CoinRemoteDataSource
    singleOf(constructor = ::CoinRemoteDataSourceImpl).bind<CoinRemoteDataSource>()

    // provide a single instance of CoinRepoImpl which implements CoinRepo
    singleOf(constructor = ::CoinRepoImpl).bind<CoinRepo>()


    viewModelOf(constructor = ::CoinListViewModel)

    // --- Use Cases ---
    factory { LoadCoinsUseCase(coinRepo = get()) }
    factory { LoadCoinHistoryUseCase(coinRepo = get()) }


}