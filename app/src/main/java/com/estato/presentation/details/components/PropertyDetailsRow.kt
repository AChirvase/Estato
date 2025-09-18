package com.estato.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.estato.presentation.details.RealEstateDetailsUiModel

@Composable
fun PropertyDetailsRow(realEstate: RealEstateDetailsUiModel) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PropertyDetailItem(
            icon = Icons.Default.Check,
            label = "Bedrooms",
            value = realEstate.bedrooms.toString()
        )
        PropertyDetailItem(
            icon = Icons.Default.Check,
            label = "Bathrooms",
            value = realEstate.bathrooms.toString()
        )
        PropertyDetailItem(
            icon = Icons.Default.Check,
            label = "Area",
            value = realEstate.area
        )
    }
}

@Composable
private fun PropertyDetailItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
