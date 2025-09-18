package com.estato.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.estato.presentation.details.DetailsViewState

@Composable
fun DetailsScaffoldContent(
    state: DetailsViewState,
    paddingValues: androidx.compose.foundation.layout.PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when {
            state.isLoading -> {
                LoadingState(modifier = Modifier.align(Alignment.Center))
            }
            state.errorMessage != null -> {
                ErrorState(
                    errorMessage = state.errorMessage!!,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.realEstate != null -> {
                DetailsContent(realEstate = state.realEstate!!)
            }
        }
    }
}
