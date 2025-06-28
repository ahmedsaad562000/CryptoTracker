package com.plcoding.cryptotracker

import android.app.Application
import android.util.Log
import com.plcoding.cryptotracker.di.appModule
import io.ktor.client.plugins.logging.LogLevel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class CryptoTrackerApp: Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(androidContext = this@CryptoTrackerApp)
            androidLogger(Level.INFO)
            modules(appModule)
        }


    }
}