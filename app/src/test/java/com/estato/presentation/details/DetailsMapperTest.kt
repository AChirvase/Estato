package com.estato.presentation.details

import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailsMapperTest {

    private lateinit var mapper: DetailsMapper

    @BeforeEach
    fun setup() {
        mapper = DetailsMapper()
    }

    @Test
    fun `given domain model, when mapToScreenData, then correctly map to UI model`() {
        val villersSurMerVillaDomainModel = RealEstate(
            id = "1",
            title = "Maison - Villa in Villers-sur-Mer",
            description = "Beautiful villa with sea view",
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

        val actualDetailsUiModel = mapper.mapToScreenData(villersSurMerVillaDomainModel)
        val expectedDetailsUiModel = createExpectedRealEstateDetailsUiModel(
            id = "1",
            title = "Maison - Villa in Villers-sur-Mer",
            description = "Beautiful villa with sea view",
            price = "€1,500,000",
            location = "Villers-sur-Mer",
            imageUrl = "villa.jpg",
            type = "Villa",
            bedrooms = 4,
            bathrooms = 3,
            area = "250 m²",
            isForSale = true,
            agentName = "GSL EXPLORE",
            phoneNumber = "+33 1 00 00 00 00",
            email = "gsl.explore@gsl.com"
        )

        assertEquals("1", actualDetailsUiModel.id)
        assertEquals("Villa", actualDetailsUiModel.type)
        assertTrue(actualDetailsUiModel.price.contains("1") && actualDetailsUiModel.price.contains("500"))
    }

    @Test
    fun `given domain model with USD currency, when mapToScreenData, then format prices correctly`() {
        val newYorkUsdApartmentDomainModel = RealEstate(
            id = "1",
            title = "Test Property",
            description = "Test description",
            price = 500000.0,
            currency = "USD",
            location = "New York",
            imageUrl = "test.jpg",
            type = RealEstateType.APARTMENT,
            bedrooms = 2,
            bathrooms = 2,
            area = 100.0,
            isForSale = true,
            contactInfo = ContactInfo("Agent", "+1234567890", "agent@test.com")
        )

        val detailsUiModel = mapper.mapToScreenData(newYorkUsdApartmentDomainModel)

        assertEquals("$500,000.00", detailsUiModel.price)
    }

    @Test
    fun `given domain model with unknown currency, when mapToScreenData, then format currency correctly`() {
        val londonGbpHouseDomainModel = RealEstate(
            id = "1",
            title = "Test Property",
            description = "Test description",
            price = 500000.0,
            currency = "GBP",
            location = "London",
            imageUrl = "test.jpg",
            type = RealEstateType.HOUSE,
            bedrooms = 3,
            bathrooms = 2,
            area = 150.0,
            isForSale = false,
            contactInfo = ContactInfo("Agent", "+44123456789", "agent@test.com")
        )

        val detailsUiModel = mapper.mapToScreenData(londonGbpHouseDomainModel)

        assertEquals("GBP 500,000", detailsUiModel.price)
    }

    @Test
    fun `given domain models with different property types, when mapToScreenData, then handle property types correctly`() {
        val apartmentDomainModel = createRealEstate(type = RealEstateType.APARTMENT)
        val houseDomainModel = createRealEstate(type = RealEstateType.HOUSE)
        val villaDomainModel = createRealEstate(type = RealEstateType.VILLA)
        val commercialDomainModel = createRealEstate(type = RealEstateType.COMMERCIAL)

        val apartmentDetailsUiModel = mapper.mapToScreenData(apartmentDomainModel)
        val houseDetailsUiModel = mapper.mapToScreenData(houseDomainModel)
        val villaDetailsUiModel = mapper.mapToScreenData(villaDomainModel)
        val commercialDetailsUiModel = mapper.mapToScreenData(commercialDomainModel)

        assertEquals("Apartment", apartmentDetailsUiModel.type)
        assertEquals("House", houseDetailsUiModel.type)
        assertEquals("Villa", villaDetailsUiModel.type)
        assertEquals("Commercial", commercialDetailsUiModel.type)
    }

    @Test
    fun `given domain model with decimal area, when mapToScreenData, then format area correctly`() {
        val decimalAreaDomainModel = createRealEstate(area = 125.5)

        val detailsUiModel = mapper.mapToScreenData(decimalAreaDomainModel)

        assertEquals("125 m²", detailsUiModel.area)
    }

    private fun createExpectedRealEstateDetailsUiModel(
        id: String = "1",
        title: String = "Test Property",
        description: String = "Test description",
        price: String = "€100,000",
        location: String = "Test City",
        imageUrl: String = "test.jpg",
        type: String = "Apartment",
        bedrooms: Int = 2,
        bathrooms: Int = 1,
        area: String = "100 m²",
        isForSale: Boolean = true,
        agentName: String = "Agent",
        phoneNumber: String = "+1234567890",
        email: String = "agent@test.com"
    ): RealEstateDetailsUiModel {
        return RealEstateDetailsUiModel(
            id = id,
            title = title,
            description = description,
            price = price,
            location = location,
            imageUrl = imageUrl,
            type = type,
            bedrooms = bedrooms,
            bathrooms = bathrooms,
            area = area,
            isForSale = isForSale,
            agentName = agentName,
            phoneNumber = phoneNumber,
            email = email
        )
    }

    private fun createRealEstate(
        id: String = "1",
        title: String = "Test Property",
        description: String = "Test description",
        price: Double = 100000.0,
        currency: String = "EUR",
        location: String = "Test City",
        imageUrl: String = "test.jpg",
        type: RealEstateType = RealEstateType.APARTMENT,
        bedrooms: Int = 2,
        bathrooms: Int = 1,
        area: Double = 100.0,
        isForSale: Boolean = true,
        contactInfo: ContactInfo = ContactInfo("Agent", "+1234567890", "agent@test.com")
    ): RealEstate {
        return RealEstate(
            id = id,
            title = title,
            description = description,
            price = price,
            currency = currency,
            location = location,
            imageUrl = imageUrl,
            type = type,
            bedrooms = bedrooms,
            bathrooms = bathrooms,
            area = area,
            isForSale = isForSale,
            contactInfo = contactInfo
        )
    }
}