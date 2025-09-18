package com.estato.presentation.listing.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.estato.core.constants.AppConstants
import com.estato.presentation.commons.PropertyDetail
import com.estato.presentation.listing.RealEstateUiModel

@Composable
fun RealEstateCardContent(realEstate: RealEstateUiModel) {
    Column(
        modifier = Modifier.padding(AppConstants.CONTENT_PADDING_DP.dp)
    ) {
        RealEstateHeader(realEstate)
        Spacer(modifier = Modifier.height(AppConstants.SPACING_LARGE_DP.dp))
        RealEstateDetails(realEstate)
    }
}

@Composable
private fun RealEstateHeader(realEstate: RealEstateUiModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = realEstate.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(AppConstants.SPACING_SMALL_DP.dp))
            Text(
                text = realEstate.location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = realEstate.price,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun RealEstateDetails(realEstate: RealEstateUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppConstants.SPACING_XL_DP.dp)
    ) {
        PropertyDetail(
            icon = Icons.Default.Check,
            value = realEstate.bedrooms.toString()
        )
        PropertyDetail(
            icon = Icons.Default.Check,
            value = realEstate.bathrooms.toString()
        )
        PropertyDetail(
            icon = Icons.Default.Check,
            value = realEstate.area
        )
    }
}
