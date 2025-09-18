package com.estato.domain.repository

import com.estato.domain.model.RealEstate
import kotlinx.coroutines.flow.Flow

interface RealEstateRepository {
    fun getRealEstates(): Flow<List<RealEstate>>
    suspend fun getRealEstateById(id: String): RealEstate?
    suspend fun refreshRealEstates()
}
