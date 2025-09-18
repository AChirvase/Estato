package com.estato.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.estato.core.constants.AppConstants
import com.estato.presentation.details.RealEstateDetailsUiModel

@Composable
fun DetailsContent(realEstate: RealEstateDetailsUiModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = realEstate.imageUrl,
            contentDescription = realEstate.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppConstants.DETAIL_IMAGE_HEIGHT_DP.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(AppConstants.CONTENT_PADDING_DP.dp)
        ) {
            PropertyHeader(realEstate = realEstate)

            Spacer(modifier = Modifier.height(16.dp))

            PropertyDetailsRow(realEstate = realEstate)

            Spacer(modifier = Modifier.height(24.dp))

            PropertyDescription(description = realEstate.description)

            Spacer(modifier = Modifier.height(24.dp))

            ContactCard(realEstate = realEstate)
        }
    }
}
