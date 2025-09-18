package com.estato.presentation.listing

import com.estato.core.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    interactor: ListingInteractor,
    mapper: ListingMapper,
    reducer: ListingReducer
) : MviViewModel<ListingIntent, ListingResult, ListingViewState, ListingInteractor, ListingMapper, ListingReducer>(
    interactor = interactor,
    mapper = mapper,
    reducer = reducer,
    initialState = initialListingViewState
)