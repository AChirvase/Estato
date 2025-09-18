package com.estato.presentation.listing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.estato.core.constants.AppConstants

@Composable
fun ShimmerRealEstateList(
    scaffoldPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = scaffoldPadding.calculateTopPadding() + AppConstants.CONTENT_PADDING_DP.dp,
            bottom = scaffoldPadding.calculateBottomPadding() + AppConstants.CONTENT_PADDING_DP.dp,
            start = AppConstants.CONTENT_PADDING_DP.dp,
            end = AppConstants.CONTENT_PADDING_DP.dp
        ),
        verticalArrangement = Arrangement.spacedBy(AppConstants.SPACING_LARGE_DP.dp)
    ) {
        items(AppConstants.SHIMMER_CARD_COUNT) { // Show shimmer cards
            ShimmerRealEstateCard()
        }
    }
}
