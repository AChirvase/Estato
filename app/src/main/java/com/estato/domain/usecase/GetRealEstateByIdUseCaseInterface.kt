package com.estato.domain.usecase

import com.estato.domain.model.RealEstate

interface GetRealEstateByIdUseCaseInterface {
    suspend fun execute(id: String): RealEstate?
}
