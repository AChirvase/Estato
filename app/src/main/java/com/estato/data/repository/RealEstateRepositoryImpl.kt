package com.estato.data.repository

import com.estato.data.mapper.RealEstateMapper
import com.estato.data.remote.api.RealEstateApi
import com.estato.domain.model.RealEstate
import com.estato.domain.repository.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepositoryImpl @Inject constructor(
    private val api: RealEstateApi,
    private val mapper: RealEstateMapper
) : RealEstateRepository {

    private val _realEstates = MutableStateFlow<List<RealEstate>>(emptyList())

    override fun getRealEstates(): Flow<List<RealEstate>> {
        return _realEstates.asStateFlow()
    }

    override suspend fun getRealEstateById(id: String): RealEstate? {
        return try {
            val dto = api.getRealEstateById(id)
            mapper.mapToDomain(dto)
        } catch (e: Exception) {
            _realEstates.value.find { it.id == id }
        }
    }

    override suspend fun refreshRealEstates() {
        try {
            val dtos = api.getRealEstates()
            val domainModels = dtos.map { mapper.mapToDomain(it) }
            _realEstates.value = domainModels
        } catch (e: Exception) {
            if (_realEstates.value.isEmpty()) {
                _realEstates.value = getMockData()
            }
        }
    }

    private fun getMockData(): List<RealEstate> {
        return listOf(
            RealEstate(
                id = "1",
                title = "Modern Villa with Pool",
                description = "Beautiful modern villa with stunning ocean views and private pool. Perfect for luxury living.",
                price = 850000.0,
                currency = "USD",
                location = "Miami Beach, FL",
                imageUrl = "https://images.unsplash.com/photo-1613490493576-7fde63acd811?w=400",
                type = com.estato.domain.model.RealEstateType.VILLA,
                bedrooms = 4,
                bathrooms = 3,
                area = 300.0,
                isForSale = true,
                contactInfo = com.estato.domain.model.ContactInfo(
                    agentName = "Sarah Johnson",
                    phoneNumber = "+1-555-0123",
                    email = "sarah.johnson@estato.com"
                )
            ),
            RealEstate(
                id = "2",
                title = "Downtown Luxury Apartment",
                description = "Spacious apartment in the heart of downtown with modern amenities and city views.",
                price = 450000.0,
                currency = "USD",
                location = "Manhattan, NY",
                imageUrl = "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400",
                type = com.estato.domain.model.RealEstateType.APARTMENT,
                bedrooms = 2,
                bathrooms = 2,
                area = 120.0,
                isForSale = true,
                contactInfo = com.estato.domain.model.ContactInfo(
                    agentName = "Mike Chen",
                    phoneNumber = "+1-555-0456",
                    email = "mike.chen@estato.com"
                )
            ),
            RealEstate(
                id = "3",
                title = "Cozy Family House",
                description = "Perfect family home with large backyard and excellent school district nearby.",
                price = 320000.0,
                currency = "USD",
                location = "Austin, TX",
                imageUrl = "https://images.unsplash.com/photo-1518780664697-55e3ad937233?w=400",
                type = com.estato.domain.model.RealEstateType.HOUSE,
                bedrooms = 3,
                bathrooms = 2,
                area = 180.0,
                isForSale = true,
                contactInfo = com.estato.domain.model.ContactInfo(
                    agentName = "Emily Rodriguez",
                    phoneNumber = "+1-555-0789",
                    email = "emily.rodriguez@estato.com"
                )
            )
        )
    }
}