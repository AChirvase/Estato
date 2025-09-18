package com.estato.data.mapper

import com.estato.core.constants.AppConstants
import com.estato.data.local.entity.RealEstateEntity
import com.estato.data.remote.dto.RealEstateDto
import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import javax.inject.Inject

class RealEstateMapper @Inject constructor() {

    fun mapToDomain(dto: RealEstateDto): RealEstate {
        return RealEstate(
            id = dto.id.toString(),
            title = generateTitle(dto.propertyType, dto.city),
            description = generateDescription(dto),
            price = dto.price,
            currency = EUR_CURRENCY,
            location = dto.city,
            imageUrl = dto.url ?: getDefaultImageUrl(),
            type = mapToRealEstateType(dto.propertyType),
            bedrooms = dto.bedrooms ?: AppConstants.DEFAULT_BEDROOMS,
            bathrooms = estimateBathrooms(dto.rooms ?: AppConstants.DEFAULT_ROOMS, dto.bedrooms),
            area = dto.area,
            isForSale = dto.offerType == AppConstants.OFFER_TYPE_FOR_SALE,
            contactInfo = createContactInfo(dto.professional)
        )
    }

    fun mapToDomain(entity: RealEstateEntity): RealEstate {
        return RealEstate(
            id = entity.id.toString(),
            title = generateTitle(entity.propertyType, entity.city),
            description = generateDescriptionFromEntity(entity),
            price = entity.price,
            currency = EUR_CURRENCY,
            location = entity.city,
            imageUrl = entity.url ?: getDefaultImageUrl(),
            type = mapToRealEstateType(entity.propertyType),
            bedrooms = entity.bedrooms ?: AppConstants.DEFAULT_BEDROOMS,
            bathrooms = estimateBathrooms(entity.rooms ?: AppConstants.DEFAULT_ROOMS, entity.bedrooms),
            area = entity.area,
            isForSale = entity.offerType == AppConstants.OFFER_TYPE_FOR_SALE,
            contactInfo = createContactInfo(entity.professional)
        )
    }

    fun mapToEntity(dto: RealEstateDto): RealEstateEntity {
        return RealEstateEntity(
            id = dto.id,
            city = dto.city,
            area = dto.area,
            price = dto.price,
            professional = dto.professional,
            propertyType = dto.propertyType,
            offerType = dto.offerType,
            rooms = dto.rooms,
            bedrooms = dto.bedrooms,
            url = dto.url
        )
    }

    private fun generateTitle(propertyType: String, city: String): String {
        return "$propertyType in $city"
    }

    private fun generateDescription(dto: RealEstateDto): String {
        return buildPropertyDescription(
            PropertyDescriptionParams(
                propertyType = dto.propertyType,
                city = dto.city,
                rooms = dto.rooms,
                bedrooms = dto.bedrooms,
                area = dto.area,
                professional = dto.professional
            )
        )
    }

    private fun generateDescriptionFromEntity(entity: RealEstateEntity): String {
        return buildPropertyDescription(
            PropertyDescriptionParams(
                propertyType = entity.propertyType,
                city = entity.city,
                rooms = entity.rooms ?: AppConstants.DEFAULT_ROOMS,
                bedrooms = entity.bedrooms,
                area = entity.area,
                professional = entity.professional
            )
        )
    }

    private fun buildPropertyDescription(params: PropertyDescriptionParams): String {
        val bedroomText = params.bedrooms?.let { " including $it bedrooms" } ?: ""
        return "Beautiful ${params.propertyType.lowercase()} located in ${params.city}. " +
            "This property offers ${params.rooms ?: "several"} rooms$bedroomText " +
            "and ${params.area.toInt()} m² of living space. " +
            "Contact ${params.professional} for more information."
    }

    private data class PropertyDescriptionParams(
        val propertyType: String,
        val city: String,
        val rooms: Int?,
        val bedrooms: Int?,
        val area: Double,
        val professional: String
    )

    private fun mapToRealEstateType(propertyType: String): RealEstateType {
        return when {
            propertyType.contains("Maison", ignoreCase = true) ||
                propertyType.contains("Villa", ignoreCase = true) -> RealEstateType.VILLA
            propertyType.contains("Appartement", ignoreCase = true) -> RealEstateType.APARTMENT
            propertyType.contains("Commercial", ignoreCase = true) -> RealEstateType.COMMERCIAL
            else -> RealEstateType.HOUSE
        }
    }

    private fun estimateBathrooms(rooms: Int, bedrooms: Int?): Int {
        return when {
            rooms >= AppConstants.MIN_ROOMS_FOR_BATHROOM_3 -> AppConstants.MAX_BATHROOMS
            rooms >= AppConstants.MIN_ROOMS_FOR_BATHROOM_2 -> 2
            rooms >= AppConstants.MIN_ROOMS_FOR_BATHROOM_1 -> 1
            else -> AppConstants.DEFAULT_BATHROOMS
        }.coerceAtMost(bedrooms ?: AppConstants.DEFAULT_BATHROOMS)
    }

    private fun createContactInfo(professional: String): ContactInfo {
        return ContactInfo(
            agentName = professional,
            phoneNumber = AppConstants.FRENCH_PHONE_PREFIX,
            email = "${professional.lowercase().replace(" ", ".")}${AppConstants.EMAIL_DOMAIN}"
        )
    }

    private fun getDefaultImageUrl(): String {
        return DEFAULT_IMAGE_URL
    }

    companion object {
        private const val EUR_CURRENCY = "EUR"
        private const val DEFAULT_IMAGE_URL =
            "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=400"
    }
}
