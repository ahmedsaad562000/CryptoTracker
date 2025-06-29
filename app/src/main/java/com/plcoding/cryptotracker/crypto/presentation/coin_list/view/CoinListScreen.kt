package com.plcoding.cryptotracker.crypto.presentation.coin_list.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.core.presentation.util.toString
import com.plcoding.cryptotracker.crypto.presentation.coin_list.action.CoinListAction
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.CoinListItem
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.CoinListShimmer
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_model.CoinListViewModel
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_state.CoinListViewState
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel<CoinListViewModel>(),
    state: CoinListViewState = viewModel.state.collectAsStateWithLifecycle().value
) {
    val context = LocalContext.current
    val pullRefreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.sendAction(CoinListAction.onRefresh) },
        modifier = modifier,
        state = pullRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isRefreshing,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = pullRefreshState
            )
        }
    ) {

        if (state.isLoading) {
            CoinListShimmer(
                modifier = modifier.fillMaxSize(),
            )


        } else {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.coins)
                { coin ->

                    CoinListItem(
                        coinUi = coin,
                        onClick = { viewModel.sendAction(CoinListAction.onCoinClick(coin)) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    HorizontalDivider()
                }

            }

        }

        if (state.error != null) {
            Toast.makeText(
                context,
                state.error.toString(context),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinListPreview() {
    CryptoTrackerTheme {
        CoinListScreen(
            viewModel = koinViewModel<CoinListViewModel>(),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}
