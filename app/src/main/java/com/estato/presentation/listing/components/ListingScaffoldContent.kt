package com.estato.presentation.listing.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.estato.presentation.listing.ListingViewState

@Composable
fun ListingScaffoldContent(
    state: ListingViewState,
    onNavigateToDetails: (String) -> Unit,
    paddingValues: PaddingValues
) {
    when {
        state.isLoading && state.realEstates.isEmpty() -> {
            ShimmerRealEstateList(scaffoldPadding = paddingValues)
        }
        state.errorMessage != null && state.realEstates.isEmpty() -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ListingErrorState(
                    errorMessage = state.errorMessage!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        else -> {
            RealEstateList(
                realEstates = state.realEstates,
                onItemClick = onNavigateToDetails,
                scaffoldPadding = paddingValues
            )
        }
    }
}
