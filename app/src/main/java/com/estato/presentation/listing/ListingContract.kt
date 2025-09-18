package com.estato.presentation.listing

import com.estato.core.mvi.MviIntent
import com.estato.core.mvi.MviResult
import com.estato.core.mvi.MviViewState

sealed interface ListingIntent : MviIntent {
    object LoadRealEstates : ListingIntent
    object RefreshRealEstates : ListingIntent
    data class NavigateToDetails(val realEstateId: String) : ListingIntent
}

sealed interface ListingResult : MviResult {
    object Loading : ListingResult
    data class Success(val realEstates: List<RealEstateUiModel>) : ListingResult
    data class Error(val message: String) : ListingResult
}

data class ListingViewState(
    val isLoading: Boolean = false,
    val realEstates: List<RealEstateUiModel> = emptyList(),
    val errorMessage: String? = null
) : MviViewState

data class RealEstateUiModel(
    val id: String,
    val title: String,
    val location: String,
    val price: String,
    val imageUrl: String,
    val type: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: String
)

val initialListingViewState = ListingViewState()
