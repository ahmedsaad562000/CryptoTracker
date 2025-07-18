package com.plcoding.cryptotracker.crypto.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val rank: Int?,
    val symbol: String?,
    val currentPrice: Double?,
    val marketCap: Double?,
    val priceChangePercentage24h: Double?,
    val lastUpdated: Long
)