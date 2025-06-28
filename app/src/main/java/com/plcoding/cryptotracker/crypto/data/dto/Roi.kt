package com.plcoding.cryptotracker.crypto.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Roi(
    @SerialName(value = "currency") val currency: String?,
    @SerialName(value = "percentage") val percentage: Double?,
    @SerialName(value = "times") val times: Double?
)