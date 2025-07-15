package com.plcoding.cryptotracker.crypto.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinEntity
import com.plcoding.cryptotracker.crypto.data.local.entity.CoinHistoryEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): List<CoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCoins(vararg coins: CoinEntity)

    @Query("Select * from coinsHistory where id = :id")
    suspend fun getCoinHistory(id: String): List<CoinHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinHistory(vararg coinsHistory: CoinHistoryEntity)

    @Query("Delete from coinsHistory where id = :id")
    suspend fun deleteCoinHistory(id: String)
}