package com.estato.presentation.listing

import com.estato.core.mvi.MviReducer
import javax.inject.Inject

class ListingReducer @Inject constructor() : MviReducer<ListingResult, ListingViewState> {

    override fun reduce(currentState: ListingViewState, result: ListingResult): ListingViewState {
        return when (result) {
            is ListingResult.Loading -> currentState.copy(
                isLoading = true,
                errorMessage = null
            )
            is ListingResult.Success -> currentState.copy(
                isLoading = false,
                realEstates = result.realEstates,
                errorMessage = null
            )
            is ListingResult.Error -> currentState.copy(
                isLoading = false,
                errorMessage = result.message
            )
        }
    }
}
