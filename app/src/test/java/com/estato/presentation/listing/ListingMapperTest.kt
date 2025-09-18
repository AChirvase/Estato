package com.estato.presentation.listing

import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingMapperTest {

    private lateinit var mapper: ListingMapper

    @BeforeEach
    fun setup() {
        mapper = ListingMapper()
    }

    @Test
    fun `mapToScreenData should correctly map domain model to UI model`() {
        // Given
        val domainModel = RealEstate(
            id = "1",
            title = "Modern Villa",
            description = "Beautiful villa",
            price = 850000.0,
            currency = "USD",
            location = "Miami Beach",
            imageUrl = "villa.jpg",
            type = RealEstateType.VILLA,
            bedrooms = 4,
            bathrooms = 3,
            area = 300.0,
            isForSale = true,
            contactInfo = ContactInfo("Sarah", "555-0123", "sarah@test.com")
        )

        // When
        val result = mapper.mapToScreenData(domainModel)

        // Then
        assertEquals("1", result.id)
        assertEquals("Modern Villa", result.title)
        assertEquals("Miami Beach", result.location)
        assertEquals("$850,000.00", result.price)
        assertEquals("villa.jpg", result.imageUrl)
        assertEquals("Villa", result.type)
        assertEquals(4, result.bedrooms)
        assertEquals(3, result.bathrooms)
        assertEquals("300 m²", result.area)
    }
}