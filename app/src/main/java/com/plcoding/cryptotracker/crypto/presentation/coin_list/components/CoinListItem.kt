package com.plcoding.cryptotracker.crypto.presentation.coin_list.components

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.plcoding.cryptotracker.crypto.presentation.models.CoinUi

@Composable
fun CoinListItem (
    coinUi: CoinUi,
    onClick: () ->Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier.clickable(onClick = onClick).padding(dimensionResource(R.dimen.padding_16))
    ) {


    }

}