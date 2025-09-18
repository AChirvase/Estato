package com.estato.data.remote.api

import com.estato.data.remote.dto.ListingsResponseDto
import com.estato.data.remote.dto.RealEstateDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RealEstateApi {
    @GET("listings.json")
    suspend fun getRealEstates(): ListingsResponseDto

    @GET("listings/{id}.json")
    suspend fun getRealEstateById(@Path("id") id: Int): RealEstateDto
}
