package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CoinListShimmer(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(15) {
            CoinListItemShimmer(
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
        }
    }

}