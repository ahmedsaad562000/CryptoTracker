package com.plcoding.cryptotracker.crypto.presentation.coin_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.usecase.LoadCoinsUseCase
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.plcoding.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.plcoding.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val loadCoinsUseCase: LoadCoinsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = CoinListState()
        )

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.onCoinClick -> TODO()
            is CoinListAction.onRefresh -> {loadCoins()}

        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            loadCoinsUseCase().onSuccess { coins ->
                _state.update {
                    it.copy(
                        coins = coins.map { coin -> coin.toCoinUi() },
                        isLoading = false
                    )
                }


            }.onError {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

}