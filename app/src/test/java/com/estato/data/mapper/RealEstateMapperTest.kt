package com.estato.data.mapper

import com.estato.core.constants.AppConstants
import com.estato.data.local.entity.RealEstateEntity
import com.estato.data.remote.dto.RealEstateDto
import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RealEstateMapperTest {

    private lateinit var mapper: RealEstateMapper

    @BeforeEach
    fun setup() {
        mapper = RealEstateMapper()
    }

    @Test
    fun `given DTO with all fields, when mapToDomain, then correctly map all fields`() {
        val parisApartmentDto = RealEstateDto(
            id = 1,
            city = "Paris",
            area = 120.0,
            price = 850000.0,
            professional = "GSL EXPLORE",
            propertyType = "Appartement",
            offerType = AppConstants.OFFER_TYPE_FOR_SALE,
            rooms = 4,
            bedrooms = 2,
            url = "https://example.com/image.jpg"
        )

        val actualDomainModel = mapper.mapToDomain(parisApartmentDto)

        assertEquals("1", actualDomainModel.id)
        assertEquals(RealEstateType.APARTMENT, actualDomainModel.type)
        assertEquals("GSL EXPLORE", actualDomainModel.contactInfo.agentName)
    }

    @Test
    fun `given Entity with all fields, when mapToDomain, then correctly map all fields`() {
        val lyonVillaEntity = RealEstateEntity(
            id = 1,
            city = "Lyon",
            area = 250.0,
            price = 1200000.0,
            professional = "GSL PREMIUM",
            propertyType = "Maison - Villa",
            offerType = AppConstants.OFFER_TYPE_FOR_SALE,
            rooms = 6,
            bedrooms = 4,
            url = "https://example.com/villa.jpg"
        )

        val actualDomainModel = mapper.mapToDomain(lyonVillaEntity)

        assertEquals("1", actualDomainModel.id)
        assertEquals(RealEstateType.VILLA, actualDomainModel.type)
        assertEquals("GSL PREMIUM", actualDomainModel.contactInfo.agentName)
    }

    @Test
    fun `given DTO, when mapToEntity, then correctly convert to Entity`() {
        val bordeauxHouseDto = RealEstateDto(
            id = 1,
            city = "Bordeaux",
            area = 180.0,
            price = 650000.0,
            professional = "GSL OWNERS",
            propertyType = "Maison - Villa",
            offerType = 2,
            rooms = 5,
            bedrooms = 3,
            url = "https://example.com/house.jpg"
        )

        val actualEntity = mapper.mapToEntity(bordeauxHouseDto)
        val expectedEntity = RealEstateEntity(
            id = 1,
            city = "Bordeaux",
            area = 180.0,
            price = 650000.0,
            professional = "GSL OWNERS",
            propertyType = "Maison - Villa",
            offerType = 2,
            rooms = 5,
            bedrooms = 3,
            url = "https://example.com/house.jpg"
        )

        assertEquals(expectedEntity, actualEntity)
    }

    @Test
    fun `given DTO with missing url, when mapToDomain, then handle with default image`() {
        val niceApartmentDtoWithMissingUrl = RealEstateDto(
            id = 1,
            city = "Nice",
            area = 90.0,
            price = 450000.0,
            professional = "GSL CONTACTING",
            propertyType = "Appartement",
            offerType = AppConstants.OFFER_TYPE_FOR_SALE,
            rooms = 3,
            bedrooms = 1,
            url = null
        )

        val mappedRealEstate = mapper.mapToDomain(niceApartmentDtoWithMissingUrl)

        assertNotNull(mappedRealEstate.imageUrl)
        assertTrue(mappedRealEstate.imageUrl.contains("unsplash.com"))
    }

    @Test
    fun `given DTO with missing bedrooms, when mapToDomain, then handle with default value`() {
        val marseilleApartmentDtoWithMissingBedrooms = RealEstateDto(
            id = 1,
            city = "Marseille",
            area = 75.0,
            price = 300000.0,
            professional = "GSL TEST",
            propertyType = "Appartement",
            offerType = AppConstants.OFFER_TYPE_FOR_SALE,
            rooms = 2,
            bedrooms = null,
            url = "https://example.com/apt.jpg"
        )

        val mappedRealEstate = mapper.mapToDomain(marseilleApartmentDtoWithMissingBedrooms)

        assertEquals(AppConstants.DEFAULT_BEDROOMS, mappedRealEstate.bedrooms)
    }

    @Test
    fun `given DTO with missing rooms, when mapToDomain, then handle with default value`() {
        val toulouseApartmentDtoWithMissingRooms = RealEstateDto(
            id = 1,
            city = "Toulouse",
            area = 100.0,
            price = 400000.0,
            professional = "GSL TEST",
            propertyType = "Appartement",
            offerType = AppConstants.OFFER_TYPE_FOR_SALE,
            rooms = null,
            bedrooms = 2,
            url = "https://example.com/apt.jpg"
        )

        val mappedRealEstate = mapper.mapToDomain(toulouseApartmentDtoWithMissingRooms)

        assertEquals(AppConstants.DEFAULT_BATHROOMS, mappedRealEstate.bathrooms)
    }

    @Test
    fun `given DTOs with different property types, when mapToDomain, then correctly map property types`() {
        // Test different property types
        val apartmentDto = createDto(propertyType = "Appartement")
        val villaDto = createDto(propertyType = "Maison - Villa")
        val houseDto = createDto(propertyType = "Maison")
        val commercialDto = createDto(propertyType = "Commercial")
        val unknownDto = createDto(propertyType = "Unknown Type")

        val apartmentDomainModel = mapper.mapToDomain(apartmentDto)
        val villaDomainModel = mapper.mapToDomain(villaDto)
        val houseDomainModel = mapper.mapToDomain(houseDto)
        val commercialDomainModel = mapper.mapToDomain(commercialDto)
        val unknownTypeDomainModel = mapper.mapToDomain(unknownDto)

        assertEquals(RealEstateType.APARTMENT, apartmentDomainModel.type)
        assertEquals(RealEstateType.VILLA, villaDomainModel.type)
        assertEquals(RealEstateType.VILLA, houseDomainModel.type)
        assertEquals(RealEstateType.COMMERCIAL, commercialDomainModel.type)
        assertEquals(RealEstateType.HOUSE, unknownTypeDomainModel.type)
    }

    @Test
    fun `given DTOs with different offer types, when mapToDomain, then correctly determine sale status`() {
        val forSaleDto = createDto(offerType = AppConstants.OFFER_TYPE_FOR_SALE)
        val notForSaleDto = createDto(offerType = 2)

        val forSaleDomainModel = mapper.mapToDomain(forSaleDto)
        val notForSaleDomainModel = mapper.mapToDomain(notForSaleDto)

        assertTrue(forSaleDomainModel.isForSale)
        assertFalse(notForSaleDomainModel.isForSale)
    }

    @Test
    fun `given DTOs with different room counts, when mapToDomain, then estimate bathrooms correctly`() {
        // Test bathroom estimation based on room count
        val smallDto = createDto(rooms = 2, bedrooms = 1)
        val mediumDto = createDto(rooms = 4, bedrooms = 2)
        val largeDto = createDto(rooms = 7, bedrooms = 4)

        val smallPropertyDomainModel = mapper.mapToDomain(smallDto)
        val mediumPropertyDomainModel = mapper.mapToDomain(mediumDto)
        val largePropertyDomainModel = mapper.mapToDomain(largeDto)

        assertEquals(1, smallPropertyDomainModel.bathrooms)
        assertEquals(2, mediumPropertyDomainModel.bathrooms)
        assertEquals(3, largePropertyDomainModel.bathrooms)
    }

    @Test
    fun `given DTO with professional name, when mapToDomain, then generate correct email`() {
        val dtoWithProfessionalName = createDto(professional = "GSL EXPLORE")

        val mappedRealEstate = mapper.mapToDomain(dtoWithProfessionalName)

        assertEquals("gsl.explore@gsl.com", mappedRealEstate.contactInfo.email)
    }

    @Test
    fun `given DTO with room information, when mapToDomain, then generate description with apartment type and city`() {
        val parisApartmentDtoWithRoomInfo = createDto(
            propertyType = "Appartement",
            city = "Paris",
            rooms = 4,
            bedrooms = 2,
            area = 120.0,
            professional = "GSL EXPLORE"
        )

        val mappedRealEstate = mapper.mapToDomain(parisApartmentDtoWithRoomInfo)

        assertTrue(mappedRealEstate.description.contains("appartement"))
        assertTrue(mappedRealEstate.description.contains("Paris"))
    }

    @Test
    fun `given DTO with room information, when mapToDomain, then generate description with room details`() {
        val parisApartmentDtoWithRoomInfo = createDto(
            propertyType = "Appartement",
            city = "Paris",
            rooms = 4,
            bedrooms = 2,
            area = 120.0,
            professional = "GSL EXPLORE"
        )

        val mappedRealEstate = mapper.mapToDomain(parisApartmentDtoWithRoomInfo)

        assertTrue(mappedRealEstate.description.contains("4 rooms"))
        assertTrue(mappedRealEstate.description.contains("2 bedrooms"))
        assertTrue(mappedRealEstate.description.contains("120 m²"))
    }

    @Test
    fun `given DTO with room information, when mapToDomain, then generate description with professional info`() {
        val parisApartmentDtoWithRoomInfo = createDto(
            propertyType = "Appartement",
            city = "Paris",
            rooms = 4,
            bedrooms = 2,
            area = 120.0,
            professional = "GSL EXPLORE"
        )

        val mappedRealEstate = mapper.mapToDomain(parisApartmentDtoWithRoomInfo)

        assertTrue(mappedRealEstate.description.contains("GSL EXPLORE"))
    }

    private fun createExpectedRealEstate(
        id: String = "1",
        title: String = "Test Property",
        description: String = "Beautiful appartement with 3 rooms and 2 bedrooms in an area of 100 m² managed by Test Professional",
        price: Double = 100000.0,
        currency: String = "EUR",
        location: String = "TestCity",
        imageUrl: String = "https://example.com/test.jpg",
        type: RealEstateType = RealEstateType.APARTMENT,
        bedrooms: Int = 2,
        bathrooms: Int = 2,
        area: Double = 100.0,
        isForSale: Boolean = true,
        contactInfo: ContactInfo = ContactInfo("Test Professional", "+33 1 23 45 67 89", "test.professional@gsl.com")
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

    private fun createDto(
        id: Int = 1,
        city: String = "TestCity",
        area: Double = 100.0,
        price: Double = 100000.0,
        professional: String = "Test Professional",
        propertyType: String = "Appartement",
        offerType: Int = AppConstants.OFFER_TYPE_FOR_SALE,
        rooms: Int? = 3,
        bedrooms: Int? = 2,
        url: String? = "https://example.com/test.jpg"
    ): RealEstateDto {
        return RealEstateDto(
            id = id,
            city = city,
            area = area,
            price = price,
            professional = professional,
            propertyType = propertyType,
            offerType = offerType,
            rooms = rooms,
            bedrooms = bedrooms,
            url = url
        )
    }
}