package com.estato.presentation.details

import com.estato.core.mvi.MviReducer
import javax.inject.Inject

class DetailsReducer @Inject constructor() : MviReducer<DetailsResult, DetailsViewState> {

    override fun reduce(currentState: DetailsViewState, result: DetailsResult): DetailsViewState {
        return when (result) {
            is DetailsResult.Loading -> currentState.copy(
                isLoading = true,
                errorMessage = null
            )
            is DetailsResult.Success -> currentState.copy(
                isLoading = false,
                realEstate = result.realEstate,
                errorMessage = null
            )
            is DetailsResult.Error -> currentState.copy(
                isLoading = false,
                errorMessage = result.message
            )
        }
    }
}