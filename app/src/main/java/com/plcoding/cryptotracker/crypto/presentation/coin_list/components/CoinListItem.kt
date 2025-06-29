package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.plcoding.cryptotracker.R
import com.plcoding.cryptotracker.crypto.domain.models.Coin
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi
import com.plcoding.cryptotracker.crypto.presentation.models.toCoinUi
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


@Composable
fun CoinListItem(
    coinUi: CoinUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(dimensionResource(R.dimen.padding_16)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16))
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = coinUi.iconRes),
            contentDescription = coinUi.name,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(dimensionResource(R.dimen.icon_size)),

        )
        Column (
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coinUi.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            Text(
                text = coinUi.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )
        }

        Column (
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUi.princeUsd.formatted}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            PriceChangeItem(
                change = coinUi.changePercent24Hr,

            )
        }
    }

}


@PreviewLightDark
@Composable
private fun CoinListItemPreview()
{
    CryptoTrackerTheme {
        CoinListItem(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            onClick = {/*TODO*/},
            coinUi = previewCoin
        )
    }
}

internal val previewCoin =  Coin(
    name = "Bitcoin",
    symbol = "BTC",
    id = "btc",
    rank = 15,
    marketCapUsd = 15145415155.66,
    princeUsd =  256498.15 ,
    changePercent24Hr = -0.1
).toCoinUi()



