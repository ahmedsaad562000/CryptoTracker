package com.plcoding.cryptotracker

import android.app.Application
import com.plcoding.cryptotracker.di.appModule
import com.plcoding.cryptotracker.di.localModule
import com.plcoding.cryptotracker.di.remoteModule
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
            modules(modules = listOf(appModule , remoteModule, localModule))
        }


    }
}