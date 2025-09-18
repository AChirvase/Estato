package com.estato.domain.usecase

import com.estato.domain.model.RealEstate
import com.estato.domain.repository.RealEstateRepository
import javax.inject.Inject

class GetRealEstateByIdUseCase @Inject constructor(
    private val repository: RealEstateRepository
) : GetRealEstateByIdUseCaseInterface {

    override suspend fun execute(id: String): RealEstate? {
        return repository.getRealEstateById(id)
    }
}