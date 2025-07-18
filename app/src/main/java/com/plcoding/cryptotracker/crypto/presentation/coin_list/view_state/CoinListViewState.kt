package com.plcoding.cryptotracker.crypto.presentation.coin_list.view_state

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.core.data.util.Error
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi

@Immutable
data class CoinListViewState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null,
    val isRefreshing: Boolean = false,
)