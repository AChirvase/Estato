package com.estato.presentation.listing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.estato.core.constants.AppConstants
import com.estato.presentation.commons.ShimmerBox

@Composable
fun ShimmerRealEstateCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = AppConstants.CARD_ELEVATION_DP.dp),
        shape = RoundedCornerShape(AppConstants.CARD_RADIUS_DP.dp)
    ) {
        Column {
            // Shimmer image placeholder
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppConstants.IMAGE_HEIGHT_DP.dp)
            )

            ShimmerCardContent()
        }
    }
}
