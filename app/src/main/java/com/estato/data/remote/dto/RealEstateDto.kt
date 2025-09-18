package com.estato.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ListingsResponseDto(
    val items: List<RealEstateDto>,
    val totalCount: Int
)

@Serializable
data class RealEstateDto(
    val id: Int,
    val city: String,
    val area: Double,
    val price: Double,
    val professional: String,
    val propertyType: String,
    val offerType: Int,
    val rooms: Int? = null,
    val bedrooms: Int? = null,
    val url: String? = null
)
