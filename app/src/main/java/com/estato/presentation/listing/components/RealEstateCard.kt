package com.estato.presentation.listing.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.estato.core.constants.AppConstants
import com.estato.presentation.listing.RealEstateUiModel

@Composable
fun RealEstateCard(
    realEstate: RealEstateUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = AppConstants.CARD_ELEVATION_DP.dp),
        shape = RoundedCornerShape(AppConstants.CARD_RADIUS_DP.dp)
    ) {
        Column {
            AsyncImage(
                model = realEstate.imageUrl,
                contentDescription = realEstate.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppConstants.IMAGE_HEIGHT_DP.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = AppConstants.CARD_RADIUS_DP.dp,
                            topEnd = AppConstants.CARD_RADIUS_DP.dp
                        )
                    ),
                contentScale = ContentScale.Crop
            )

            RealEstateCardContent(realEstate = realEstate)
        }
    }
}
