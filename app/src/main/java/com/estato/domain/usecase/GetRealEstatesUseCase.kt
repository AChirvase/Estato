package com.estato.domain.usecase

import com.estato.domain.model.RealEstate
import com.estato.domain.repository.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRealEstatesUseCase @Inject constructor(
    private val repository: RealEstateRepository
) {
    operator fun invoke(): Flow<List<RealEstate>> {
        return repository.getRealEstates()
    }
}