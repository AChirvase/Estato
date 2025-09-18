package com.estato.data.mapper

import com.estato.data.remote.dto.ContactInfoDto
import com.estato.data.remote.dto.RealEstateDto
import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import javax.inject.Inject

class RealEstateMapper @Inject constructor() {

    fun mapToDomain(dto: RealEstateDto): RealEstate {
        return RealEstate(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            price = dto.price,
            currency = dto.currency,
            location = dto.location,
            imageUrl = dto.imageUrl,
            type = mapToRealEstateType(dto.type),
            bedrooms = dto.bedrooms,
            bathrooms = dto.bathrooms,
            area = dto.area,
            isForSale = dto.isForSale,
            contactInfo = mapContactInfoToDomain(dto.contactInfo)
        )
    }

    private fun mapToRealEstateType(type: String): RealEstateType {
        return when (type.uppercase()) {
            "HOUSE" -> RealEstateType.HOUSE
            "APARTMENT" -> RealEstateType.APARTMENT
            "CONDO" -> RealEstateType.CONDO
            "VILLA" -> RealEstateType.VILLA
            "COMMERCIAL" -> RealEstateType.COMMERCIAL
            else -> RealEstateType.HOUSE
        }
    }

    private fun mapContactInfoToDomain(dto: ContactInfoDto): ContactInfo {
        return ContactInfo(
            agentName = dto.agentName,
            phoneNumber = dto.phoneNumber,
            email = dto.email
        )
    }
}