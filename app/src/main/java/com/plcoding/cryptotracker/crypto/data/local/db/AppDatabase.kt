package com.plcoding.cryptotracker.crypto.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.cryptotracker.crypto.data.local.dao.CoinDao
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinEntity
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinHistoryEntity


@Database(entities = [CoinEntity::class, CoinHistoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}