package com.estato.presentation.listing

import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingMapperTest {

    private lateinit var mapper: ListingMapper

    @BeforeEach
    fun setup() {
        mapper = ListingMapper()
    }

    @Test
    fun `given domain model, when mapToScreenData, then correctly map to UI model`() {
        val villersSurMerVillaDomainModel = RealEstate(
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

        val actualUiModel = mapper.mapToScreenData(villersSurMerVillaDomainModel)
        assertEquals("1", actualUiModel.id)
        assertEquals("Villa", actualUiModel.type)
        assertTrue(actualUiModel.price.contains("1") && actualUiModel.price.contains("500"))
    }

    @Test
    fun `given domain model with USD currency, when mapToScreenData, then format prices correctly`() {
        val usdPropertyDomainModel = createRealEstate(price = 500000.0, currency = "USD")

        val mappedUiModel = mapper.mapToScreenData(usdPropertyDomainModel)

        assertEquals("$500,000.00", mappedUiModel.price)
    }

    @Test
    fun `given domain model with unknown currency, when mapToScreenData, then handle currency formatting`() {
        val gbpPropertyDomainModel = createRealEstate(price = 300000.0, currency = "GBP")

        val mappedUiModel = mapper.mapToScreenData(gbpPropertyDomainModel)

        assertEquals("GBP 300,000", mappedUiModel.price)
    }

    @Test
    fun `given domain models with different property types, when mapToScreenData, then handle property types correctly`() {
        val apartmentDomainModel = createRealEstate(type = RealEstateType.APARTMENT)
        val houseDomainModel = createRealEstate(type = RealEstateType.HOUSE)
        val commercialDomainModel = createRealEstate(type = RealEstateType.COMMERCIAL)

        val apartmentUiModel = mapper.mapToScreenData(apartmentDomainModel)
        val houseUiModel = mapper.mapToScreenData(houseDomainModel)
        val commercialUiModel = mapper.mapToScreenData(commercialDomainModel)

        assertEquals("Apartment", apartmentUiModel.type)
        assertEquals("House", houseUiModel.type)
        assertEquals("Commercial", commercialUiModel.type)
    }

    @Test
    fun `given domain model with zero values, when mapToScreenData, then handle zero values correctly`() {
        val zeroValuesDomainModel = createRealEstate(
            bedrooms = 0,
            bathrooms = 0,
            area = 0.0
        )

        val mappedUiModel = mapper.mapToScreenData(zeroValuesDomainModel)

        assertEquals(0, mappedUiModel.bedrooms)
        assertEquals(0, mappedUiModel.bathrooms)
        assertEquals("0 m²", mappedUiModel.area)
    }

    @Test
    fun `given domain model with decimal area, when mapToScreenData, then handle decimal area values correctly`() {
        val decimalAreaDomainModel = createRealEstate(area = 125.7)

        val mappedUiModel = mapper.mapToScreenData(decimalAreaDomainModel)

        assertEquals("125 m²", mappedUiModel.area)
    }

    private fun createExpectedRealEstateUiModel(
        id: String = "1",
        title: String = "Test Property",
        location: String = "Test City",
        price: String = "€100,000",
        imageUrl: String = "test.jpg",
        type: String = "Apartment",
        bedrooms: Int = 2,
        bathrooms: Int = 1,
        area: String = "100 m²"
    ): RealEstateUiModel {
        return RealEstateUiModel(
            id = id,
            title = title,
            location = location,
            price = price,
            imageUrl = imageUrl,
            type = type,
            bedrooms = bedrooms,
            bathrooms = bathrooms,
            area = area
        )
    }

    private fun createRealEstate(
        id: String = "1",
        title: String = "Test Property",
        price: Double = 100000.0,
        currency: String = "EUR",
        location: String = "Test City",
        imageUrl: String = "test.jpg",
        type: RealEstateType = RealEstateType.APARTMENT,
        bedrooms: Int = 2,
        bathrooms: Int = 1,
        area: Double = 100.0
    ): RealEstate {
        return RealEstate(
            id = id,
            title = title,
            description = "Test description",
            price = price,
            currency = currency,
            location = location,
            imageUrl = imageUrl,
            type = type,
            bedrooms = bedrooms,
            bathrooms = bathrooms,
            area = area,
            isForSale = true,
            contactInfo = ContactInfo("Agent", "+1234567890", "agent@test.com")
        )
    }
}
