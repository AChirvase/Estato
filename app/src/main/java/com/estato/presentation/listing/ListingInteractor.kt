package com.estato.presentation.listing

import com.estato.core.mvi.MviInteractor
import com.estato.domain.model.RealEstate
import com.estato.domain.usecase.GetRealEstatesUseCaseInterface
import com.estato.domain.usecase.RefreshRealEstatesUseCaseInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ListingInteractor @Inject constructor(
    private val getRealEstatesUseCase: GetRealEstatesUseCaseInterface,
    private val refreshRealEstatesUseCase: RefreshRealEstatesUseCaseInterface,
    private val mapper: ListingMapper
) : MviInteractor<ListingIntent, ListingResult> {

    override fun handleIntent(intent: ListingIntent): Flow<ListingResult> {
        return when (intent) {
            is ListingIntent.LoadRealEstates -> loadRealEstates()
            is ListingIntent.RefreshRealEstates -> refreshRealEstates()
            is ListingIntent.NavigateToDetails -> flow { }
        }
    }

    private fun loadRealEstates(): Flow<ListingResult> {
        return getRealEstatesUseCase.execute()
            .map<List<RealEstate>, ListingResult> { realEstates ->
                val uiModels = realEstates.map { mapper.mapToScreenData(it) }
                ListingResult.Success(uiModels)
            }
            .onStart { emit(ListingResult.Loading) }
            .catch { emit(ListingResult.Error(it.message ?: "Unknown error")) }
    }

    private fun refreshRealEstates(): Flow<ListingResult> {
        return flow {
            emit(ListingResult.Loading)
            try {
                refreshRealEstatesUseCase.execute()
            } catch (e: Exception) {
                emit(ListingResult.Error(e.message ?: "Failed to refresh"))
            }
        }
    }
}