package com.estato.presentation.details

import com.estato.core.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    interactor: DetailsInteractor,
    mapper: DetailsMapper,
    reducer: DetailsReducer
) : MviViewModel<DetailsIntent, DetailsResult, DetailsViewState, DetailsInteractor, DetailsMapper, DetailsReducer>(
    interactor = interactor,
    mapper = mapper,
    reducer = reducer,
    initialState = initialDetailsViewState
)