package com.estato.domain.usecase

import com.estato.domain.model.RealEstate
import kotlinx.coroutines.flow.Flow

interface LoadRealEstatesWithRefreshUseCaseInterface {
    fun execute(): Flow<List<RealEstate>>
}
