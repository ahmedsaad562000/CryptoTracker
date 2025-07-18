package com.plcoding.cryptotracker.di

import androidx.room.Room
import com.plcoding.cryptotracker.crypto.data.local.dao.CoinDao
import com.plcoding.cryptotracker.crypto.data.local.data_source.CoinLocalDataSource
import com.plcoding.cryptotracker.crypto.data.local.data_source.CoinLocalDataSourceImpl
import com.plcoding.cryptotracker.crypto.data.local.db.AppDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


// the Koin module that contains all the dependencies required by the application
val localModule = module {

    // provide a single instance of Room database
    single {
        Room.databaseBuilder(
            get(),                // application context
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // provide a single instance of CoinDao
    single<CoinDao> {
        get<AppDatabase>().coinDao()
    }

    // provide a single instance of CoinLocalDataSourceImpl which implements CoinLocalDataSource
    singleOf(constructor = ::CoinLocalDataSourceImpl).bind<CoinLocalDataSource>()

}