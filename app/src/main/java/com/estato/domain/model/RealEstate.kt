package com.estato.domain.model

data class RealEstate(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val currency: String,
    val location: String,
    val imageUrl: String,
    val type: RealEstateType,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val isForSale: Boolean,
    val contactInfo: ContactInfo
)

enum class RealEstateType {
    HOUSE,
    APARTMENT,
    CONDO,
    VILLA,
    COMMERCIAL
}

data class ContactInfo(
    val agentName: String,
    val phoneNumber: String,
    val email: String
)