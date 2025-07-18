package com.plcoding.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.data.util.getLastUpdatedString
import com.plcoding.cryptotracker.core.presentation.util.toDisplayableNumber
import com.plcoding.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.plcoding.cryptotracker.crypto.presentation.coin_detail.components.LineChart
import com.plcoding.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_model.CoinListViewModel
import com.plcoding.cryptotracker.crypto.presentation.coin_list.view_state.CoinListViewState
import com.plcoding.cryptotracker.crypto.presentation.models.ChartStyle
import com.plcoding.cryptotracker.crypto.presentation.models.DataPoint
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import com.plcoding.cryptotracker.ui.theme.greenBackground
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    modifier: Modifier = Modifier,
//    viewModel: CoinListViewModel = koinViewModel<CoinListViewModel>(),
    state: CoinListViewState = koinViewModel<CoinListViewModel>().state.collectAsStateWithLifecycle().value
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }
    if (state.isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else if (state.selectedCoin != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(dimensionResource(R.dimen.padding_16)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            )
            {
                Icon(

                    imageVector = ImageVector.vectorResource(id = state.selectedCoin.iconRes),
                    contentDescription = state.selectedCoin.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                )
                Text(
                    modifier = Modifier.align(Alignment.TopEnd),
                    text = "last updated: \n ${getLastUpdatedString(state.selectedCoin.lastUpdated)}",
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.Center
                )

            }

            Text(
                text = state.selectedCoin.name,
                color = contentColor,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = state.selectedCoin.symbol,
                color = contentColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formattedValue = "$ ${state.selectedCoin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.stock),
                    contentColor = contentColor,
                )
                InfoCard(
                    title = stringResource(R.string.price),
                    formattedValue = "$ ${state.selectedCoin.princeUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.dollar),
                    contentColor = contentColor,
                )
                val isPositive = state.selectedCoin.changePercent24Hr.value > 0
                val cardColor = if (isPositive) {
                    if (isSystemInDarkTheme()) {
                        Color.Green
                    } else {
                        greenBackground
                    }
                } else {
                    MaterialTheme.colorScheme.error

                }
                val absoluteChangeFormatted =
                    (state.selectedCoin.changePercent24Hr.value * state.selectedCoin.princeUsd.value / 100).toDisplayableNumber().formatted
                InfoCard(
                    title = stringResource(R.string.change_last_24h),
                    formattedValue = "$ $absoluteChangeFormatted",
                    icon = ImageVector.vectorResource(id = if (isPositive) R.drawable.trending else R.drawable.trending_down),
                    contentColor = cardColor,
                )

            }

            AnimatedVisibility(
                visible = !state.selectedCoin.coinPriceHistory.isNullOrEmpty(),
                enter = fadeIn(),
            ) {
                var selectedDataPoint by remember {
                    mutableStateOf<DataPoint?>(null)
                }
                var labelWidth by remember {
                    mutableFloatStateOf(0f)
                }
                var totalChartWidth by remember {
                    mutableFloatStateOf(0f)
                }
                val amountOfVisibleDataPoints = if (labelWidth > 0f) {
                    ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
                } else {
                    0
                }

                val startIndex = ((state.selectedCoin.coinPriceHistory?.lastIndex
                    ?: 0) - amountOfVisibleDataPoints).coerceAtLeast(0)
                val lastIndex = state.selectedCoin.coinPriceHistory?.lastIndex ?: 0
                if (!state.selectedCoin.coinPriceHistory.isNullOrEmpty()) {
                    LineChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16 / 9f)
                            .onSizeChanged { totalChartWidth = it.width.toFloat() },
                        dataPoints = state.selectedCoin.coinPriceHistory,
                        style = ChartStyle(
                            chartLineColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                            selectedColor = MaterialTheme.colorScheme.primary,
                            helperLinesThicknessPx = 5f,
                            axisLinesThicknessPx = 5f,
                            labelFontSize = 14.sp,
                            minYLabelSpacingDp = 25.dp,
                            verticalPadding = 8.dp,
                            horizontalPadding = 8.dp,
                            xAxisLabelSpacing = 8.dp
                        ),
                        visibleDataPointsIndices = (startIndex..lastIndex),
                        unit = "$",
                        selectedDataPoint = selectedDataPoint,
                        onSelectedDataPointChange = { selectedDataPoint = it },
                        onXLabelWidthChange = { labelWidth = it },
                    )
                }
            }

        }
    }


}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview() {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListViewState(
                isLoading = false,
                selectedCoin = previewCoin
            ),
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
        )
    }

}