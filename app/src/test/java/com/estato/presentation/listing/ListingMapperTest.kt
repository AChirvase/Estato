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
            title = "Maison - Villa in Villers-sur-Mer",
            description = "Beautiful villa",
            price = 1500000.0,
            currency = "EUR",
            location = "Villers-sur-Mer",
            imageUrl = "villa.jpg",
            type = RealEstateType.VILLA,
            bedrooms = 4,
            bathrooms = 3,
            area = 250.0,
            isForSale = true,
            contactInfo = ContactInfo("GSL EXPLORE", "+33 1 00 00 00 00", "gsl.explore@gsl.com")
        )

        // When
        val result = mapper.mapToScreenData(domainModel)

        // Then
        assertEquals("1", result.id)
        assertEquals("Maison - Villa in Villers-sur-Mer", result.title)
        assertEquals("Villers-sur-Mer", result.location)
        assertEquals("€1,500,000.00", result.price)
        assertEquals("villa.jpg", result.imageUrl)
        assertEquals("Villa", result.type)
        assertEquals(4, result.bedrooms)
        assertEquals(3, result.bathrooms)
        assertEquals("250 m²", result.area)
    }
}