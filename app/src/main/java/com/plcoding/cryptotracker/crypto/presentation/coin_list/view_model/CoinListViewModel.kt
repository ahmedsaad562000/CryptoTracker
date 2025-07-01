package com.plcoding.cryptotracker.crypto.presentation.coin_list.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.usecase.LoadCoinHistoryUseCase
import com.plcoding.cryptotracker.crypto.domain.usecase.LoadCoinsUseCase
import com.plcoding.cryptotracker.crypto.presentation.coin_list.action.CoinListAction
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_state.CoinListViewState
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.models.DataPoint
import com.plcoding.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class CoinListViewModel(
    private val loadCoinsUseCase: LoadCoinsUseCase,
    private val loadCoinHistoryUseCase: LoadCoinHistoryUseCase
) : ViewModel() {
    private val _actionChannel = Channel<CoinListAction>(Channel.UNLIMITED)

    init {
        observeActions()
    }

    private val _state = MutableStateFlow(CoinListViewState())
    val state = _state
        .onStart {
            sendAction(CoinListAction.onRefresh)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = CoinListViewState()
        )

    private fun observeActions() {
        viewModelScope.launch {
            for (action in _actionChannel) {
                handleAction(action)
            }
        }
    }

    fun sendAction(action: CoinListAction) {
        viewModelScope.launch {
            _actionChannel.send(action)
        }
    }

    fun handleAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.onCoinClick -> navigateToCoinDetail(action.coinUi)
            is CoinListAction.onRefresh -> loadCoins()
            is CoinListAction.showError -> showToast(action.error)
        }
    }

    private fun navigateToCoinDetail(coin: CoinUi) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCoin = coin
                )
            }

            loadCoinHistoryUseCase(coinId = coin.id).onSuccess { history ->

                _state.update {
                    it.copy(
                        selectedCoin = it.selectedCoin?.copy(
                            coinPriceHistory = history.prices.map { coinPrice ->
                                DataPoint(
                                    xLabel = SimpleDateFormat("ha\nd/M").format(coinPrice.dateTime),
                                    y = coinPrice.priceUsd.toFloat(),
                                    x = coinPrice.dateTime.hours.toFloat()
                                )
                            }
                        )
                    )
                }
            }.onError { error ->
                showToast(error)
            }
            /* navigation */
        }
    }

    private fun showToast(error: NetworkError) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    error = error,
                )
            }
            delay(3000)
            hideToast()
        }

    }

    private fun hideToast() {
        _state.update {
            it.copy(
                error = null
            )
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true,
                    isRefreshing = true
                )
            }

            loadCoinsUseCase().onSuccess { coins ->
                _state.update {
                    it.copy(
                        coins = coins.map { coin -> coin.toCoinUi() },
                        isLoading = false,
                        isRefreshing = false
                    )
                }


            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false
                    )
                }
                sendAction(CoinListAction.showError(error))
            }
        }
    }

}