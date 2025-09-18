package com.estato.data.mapper

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
            currency = "EUR",
            location = dto.city,
            imageUrl = dto.url ?: getDefaultImageUrl(),
            type = mapToRealEstateType(dto.propertyType),
            bedrooms = dto.bedrooms ?: 0,
            bathrooms = estimateBathrooms(dto.rooms, dto.bedrooms),
            area = dto.area,
            isForSale = dto.offerType == 1,
            contactInfo = ContactInfo(
                agentName = dto.professional,
                phoneNumber = "+33 1 00 00 00 00",
                email = "${dto.professional.lowercase().replace(" ", ".")}@gsl.com"
            )
        )
    }

    fun mapToDomain(entity: RealEstateEntity): RealEstate {
        return RealEstate(
            id = entity.id.toString(),
            title = generateTitle(entity.propertyType, entity.city),
            description = generateDescription(entity),
            price = entity.price,
            currency = "EUR",
            location = entity.city,
            imageUrl = entity.url ?: getDefaultImageUrl(),
            type = mapToRealEstateType(entity.propertyType),
            bedrooms = entity.bedrooms ?: 0,
            bathrooms = estimateBathrooms(entity.rooms, entity.bedrooms),
            area = entity.area,
            isForSale = entity.offerType == 1,
            contactInfo = ContactInfo(
                agentName = entity.professional,
                phoneNumber = "+33 1 00 00 00 00",
                email = "${entity.professional.lowercase().replace(" ", ".")}@gsl.com"
            )
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
        return "Beautiful ${dto.propertyType.lowercase()} located in ${dto.city}. " +
                "This property offers ${dto.rooms} rooms" +
                (dto.bedrooms?.let { " including $it bedrooms" } ?: "") +
                " and ${dto.area.toInt()} m² of living space. " +
                "Contact ${dto.professional} for more information."
    }

    private fun generateDescription(entity: RealEstateEntity): String {
        return "Beautiful ${entity.propertyType.lowercase()} located in ${entity.city}. " +
                "This property offers ${entity.rooms} rooms" +
                (entity.bedrooms?.let { " including $it bedrooms" } ?: "") +
                " and ${entity.area.toInt()} m² of living space. " +
                "Contact ${entity.professional} for more information."
    }

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
            rooms >= 6 -> 3
            rooms >= 4 -> 2
            rooms >= 2 -> 1
            else -> 1
        }.coerceAtMost(bedrooms ?: 1)
    }

    private fun getDefaultImageUrl(): String {
        return "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?w=400"
    }
}