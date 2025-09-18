package com.estato.presentation.details

import com.estato.core.mvi.MviInteractor
import com.estato.domain.usecase.GetRealEstateByIdUseCaseInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DetailsInteractor @Inject constructor(
    private val getRealEstateByIdUseCase: GetRealEstateByIdUseCaseInterface,
    private val mapper: DetailsMapper
) : MviInteractor<DetailsIntent, DetailsResult> {

    override fun handleIntent(intent: DetailsIntent): Flow<DetailsResult> {
        return when (intent) {
            is DetailsIntent.LoadRealEstateDetails -> loadRealEstateDetails(intent.id)
            is DetailsIntent.NavigateBack -> flow { }
        }
    }

    private fun loadRealEstateDetails(id: String): Flow<DetailsResult> {
        return flow {
            val realEstate = getRealEstateByIdUseCase.execute(id)
            if (realEstate != null) {
                val uiModel = mapper.mapToScreenData(realEstate)
                emit(DetailsResult.Success(uiModel))
            } else {
                emit(DetailsResult.Error("Real estate not found"))
            }
        }
            .onStart { emit(DetailsResult.Loading) }
            .catch { emit(DetailsResult.Error(it.message ?: "Unknown error")) }
    }
}