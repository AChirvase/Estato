package com.estato.core.mvi

import kotlinx.coroutines.flow.Flow

interface MviIntent

interface MviResult

interface MviViewState

interface MviInteractor<I : MviIntent, R : MviResult> {
    fun handleIntent(intent: I): Flow<R>
}

interface MviMapper<DomainData, ScreenData> {
    fun mapToScreenData(domainData: DomainData): ScreenData
}

interface MviReducer<R : MviResult, VS : MviViewState> {
    fun reduce(currentState: VS, result: R): VS
}
