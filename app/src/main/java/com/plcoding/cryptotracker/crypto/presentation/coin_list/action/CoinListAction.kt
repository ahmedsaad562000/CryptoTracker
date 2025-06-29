package com.plcoding.cryptotracker.crypto.presentation.coin_list.action

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class onCoinClick(val coinUi: CoinUi) : CoinListAction
    data object onRefresh : CoinListAction
    data class showError(val error: NetworkError) : CoinListAction
}