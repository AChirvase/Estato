package com.estato.presentation.listing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.estato.core.constants.AppConstants
import com.estato.presentation.commons.ShimmerBox

@Composable
fun ShimmerCardContent() {
    Column(
        modifier = Modifier.padding(AppConstants.CONTENT_PADDING_DP.dp)
    ) {
        ShimmerCardHeader()
        Spacer(modifier = Modifier.height(AppConstants.SPACING_LARGE_DP.dp))
        ShimmerCardDetails()
    }
}

@Composable
private fun ShimmerCardHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            // Title shimmer
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(AppConstants.SHIMMER_TITLE_WIDTH_FRACTION)
                    .height(AppConstants.SHIMMER_TITLE_HEIGHT.dp)
            )
            Spacer(modifier = Modifier.height(AppConstants.SPACING_SMALL_DP.dp))
            // Location shimmer
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(AppConstants.SHIMMER_SUBTITLE_WIDTH_FRACTION)
                    .height(AppConstants.SHIMMER_SUBTITLE_HEIGHT.dp)
            )
        }
        // Price shimmer
        ShimmerBox(
            modifier = Modifier
                .width(AppConstants.SHIMMER_PRICE_WIDTH.dp)
                .height(AppConstants.SHIMMER_TITLE_HEIGHT.dp)
        )
    }
}

@Composable
private fun ShimmerCardDetails() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppConstants.SPACING_XL_DP.dp)
    ) {
        repeat(AppConstants.SHIMMER_DETAILS_REPEAT_COUNT) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShimmerBox(
                    modifier = Modifier.size(AppConstants.ICON_SIZE_DP.dp)
                )
                Spacer(modifier = Modifier.width(AppConstants.SPACING_SMALL_DP.dp))
                ShimmerBox(
                    modifier = Modifier
                        .width(AppConstants.SHIMMER_DETAIL_WIDTH.dp)
                        .height(AppConstants.SHIMMER_SUBTITLE_HEIGHT.dp)
                )
            }
        }
    }
}
