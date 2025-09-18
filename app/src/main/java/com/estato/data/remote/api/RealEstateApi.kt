package com.estato.data.remote.api

import com.estato.data.remote.dto.RealEstateDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RealEstateApi {
    @GET("real-estates")
    suspend fun getRealEstates(): List<RealEstateDto>

    @GET("real-estates/{id}")
    suspend fun getRealEstateById(@Path("id") id: String): RealEstateDto
}