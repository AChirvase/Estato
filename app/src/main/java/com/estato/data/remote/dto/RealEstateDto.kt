package com.estato.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RealEstateDto(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val currency: String,
    val location: String,
    val imageUrl: String,
    val type: String,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val isForSale: Boolean,
    val contactInfo: ContactInfoDto
)

@Serializable
data class ContactInfoDto(
    val agentName: String,
    val phoneNumber: String,
    val email: String
)