package com.estato.domain.usecase

import com.estato.domain.repository.RealEstateRepository
import javax.inject.Inject

class RefreshRealEstatesUseCase @Inject constructor(
    private val repository: RealEstateRepository
) {
    suspend operator fun invoke() {
        repository.refreshRealEstates()
    }
}