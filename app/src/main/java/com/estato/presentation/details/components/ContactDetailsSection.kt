package com.estato.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
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
fun ContactDetailsSection(realEstate: RealEstateDetailsUiModel) {
    Text(
        text = "Contact Information",
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(16.dp))

    ContactDetail(
        icon = Icons.Default.Phone,
        label = "Agent",
        value = realEstate.agentName
    )
    Spacer(modifier = Modifier.height(12.dp))

    ContactDetail(
        icon = Icons.Default.Phone,
        label = "Phone",
        value = realEstate.phoneNumber
    )
    Spacer(modifier = Modifier.height(12.dp))

    ContactDetail(
        icon = Icons.Default.Email,
        label = "Email",
        value = realEstate.email
    )
}

@Composable
private fun ContactDetail(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
