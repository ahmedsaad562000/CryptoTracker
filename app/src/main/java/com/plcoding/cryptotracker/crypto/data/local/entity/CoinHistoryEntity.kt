package com.plcoding.cryptotracker.crypto.data.local.entity

import androidx.room.Entity

@Entity(tableName = "coinsHistory" , primaryKeys = ["id", "dateTime", "lastUpdated"])
data class CoinHistoryEntity(
    val id: String,
    val price: Double,
    val dateTime: Double,
    val lastUpdated: Long
)