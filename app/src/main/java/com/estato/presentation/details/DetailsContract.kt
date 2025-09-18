package com.estato.presentation.details

import com.estato.core.mvi.MviIntent
import com.estato.core.mvi.MviResult
import com.estato.core.mvi.MviViewState

sealed interface DetailsIntent : MviIntent {
    data class LoadRealEstateDetails(val id: String) : DetailsIntent
    object NavigateBack : DetailsIntent
}

sealed interface DetailsResult : MviResult {
    object Loading : DetailsResult
    data class Success(val realEstate: RealEstateDetailsUiModel) : DetailsResult
    data class Error(val message: String) : DetailsResult
}

data class DetailsViewState(
    val isLoading: Boolean = false,
    val realEstate: RealEstateDetailsUiModel? = null,
    val errorMessage: String? = null
) : MviViewState

data class RealEstateDetailsUiModel(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val location: String,
    val imageUrl: String,
    val type: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: String,
    val isForSale: Boolean,
    val agentName: String,
    val phoneNumber: String,
    val email: String
)

val initialDetailsViewState = DetailsViewState()