package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.core.presentation.util.shimmerEffect
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItemShimmer(modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .padding(
                horizontal = dimensionResource(R.dimen.padding_16),
                vertical = 8.dp
            )
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16))
    ) {
        Box(
            modifier = Modifier
                .size(dimensionResource(R.dimen.icon_size))
                .shimmerEffect(toShow = true),
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .width(80.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Box(

                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth(0.3f)
                    .fillMaxWidth()
                    .shimmerEffect(toShow = true)
            )
            Box(

                modifier = Modifier
                    .height(7.dp)
                    .fillMaxWidth(0.4f)
                    .shimmerEffect(toShow = true)
            )
        }

        Column(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.End,
        ) {
            Box(

                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth()
                    .shimmerEffect(toShow = true)
            )
            Box(

                modifier = Modifier
                    .height(15.dp)
                    .fillMaxWidth(0.8f)
                    .shimmerEffect(toShow = true)
            )
        }

    }

}

@PreviewLightDark
@Composable
private fun CoinListItemShimmerPreview() {
    CryptoTrackerTheme {
        CoinListItemShimmer(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.icon_size))
        )
    }

}