package com.estato.core.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<I : MviIntent, R : MviResult, VS : MviViewState, Interactor : MviInteractor<I, R>, Mapper : MviMapper<*, *>, Reducer : MviReducer<R, VS>>(
    private val interactor: Interactor,
    private val mapper: Mapper,
    private val reducer: Reducer,
    initialState: VS
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<VS> = _state.asStateFlow()

    fun handleIntent(intent: I) {
        viewModelScope.launch {
            interactor.handleIntent(intent).collect { result ->
                _state.value = reducer.reduce(_state.value, result)
            }
        }
    }
}