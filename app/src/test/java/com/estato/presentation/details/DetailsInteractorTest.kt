package com.estato.presentation.details

import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import com.estato.domain.usecase.GetRealEstateByIdUseCaseInterface
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class DetailsInteractorTest {

    @Mock
    private lateinit var getRealEstateByIdUseCase: GetRealEstateByIdUseCaseInterface

    @Mock
    private lateinit var mapper: DetailsMapper

    private lateinit var interactor: DetailsInteractor

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = DetailsInteractor(
            getRealEstateByIdUseCase,
            mapper
        )
    }

    @Test
    fun `given LoadRealEstateDetails intent, when handleIntent, then emit Loading then Success`() = runTest {
        val requestedPropertyId = "1"
        val niceVillaDomainModel = createRealEstate(id = requestedPropertyId)
        val niceVillaDetailsUiModel = createRealEstateDetailsUiModel(id = requestedPropertyId)

        whenever(getRealEstateByIdUseCase.execute(requestedPropertyId))
            .thenReturn(niceVillaDomainModel)
        whenever(mapper.mapToScreenData(niceVillaDomainModel))
            .thenReturn(niceVillaDetailsUiModel)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(requestedPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Success(niceVillaDetailsUiModel)
        )

        assertEquals(expectedResults, emittedResults)
    }

    @Test
    fun `given LoadRealEstateDetails intent and property not found, when handleIntent, then emit Error`() = runTest {
        val nonExistentPropertyId = "999"

        whenever(getRealEstateByIdUseCase.execute(nonExistentPropertyId))
            .thenReturn(null)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(nonExistentPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Error("Real estate not found")
        )

        assertEquals(expectedResults, emittedResults)
    }

    @Test
    fun `given LoadRealEstateDetails intent and use case throws exception, when handleIntent, then emit Error`() = runTest {
        val requestedPropertyId = "1"
        val databaseErrorMessage = "Database error"
        val databaseException = RuntimeException(databaseErrorMessage)

        whenever(getRealEstateByIdUseCase.execute(requestedPropertyId))
            .thenThrow(databaseException)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(requestedPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Error(databaseErrorMessage)
        )

        assertEquals(expectedResults, emittedResults)
    }

    @Test
    fun `given LoadRealEstateDetails intent and unknown error, when handleIntent, then handle unknown error`() = runTest {
        val requestedPropertyId = "1"
        val unknownRuntimeException = RuntimeException()

        whenever(getRealEstateByIdUseCase.execute(requestedPropertyId))
            .thenThrow(unknownRuntimeException)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(requestedPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Error("Unknown error")
        )

        assertEquals(expectedResults, emittedResults)
    }

    @Test
    fun `given LoadRealEstateDetails intent with different property types, when handleIntent, then map correctly`() = runTest {
        val requestedPropertyId = "1"
        val beautifulApartmentDomainModel = createRealEstate(
            id = requestedPropertyId,
            type = RealEstateType.APARTMENT,
            title = "Beautiful Apartment"
        )
        val beautifulApartmentDetailsUiModel = createRealEstateDetailsUiModel(
            id = requestedPropertyId,
            type = "Apartment",
            title = "Beautiful Apartment"
        )

        whenever(getRealEstateByIdUseCase.execute(requestedPropertyId))
            .thenReturn(beautifulApartmentDomainModel)
        whenever(mapper.mapToScreenData(beautifulApartmentDomainModel))
            .thenReturn(beautifulApartmentDetailsUiModel)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(requestedPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Success(beautifulApartmentDetailsUiModel)
        )

        assertEquals(expectedResults, emittedResults)
    }

    @Test
    fun `given NavigateBack intent, when handleIntent, then emit empty flow`() = runTest {
        val navigateBackIntent = DetailsIntent.NavigateBack

        val emittedResults = interactor.handleIntent(navigateBackIntent).toList()

        assertEquals(0, emittedResults.size)
    }

    @Test
    fun `given LoadRealEstateDetails intent with different contact info, when handleIntent, then handle contact info correctly`() = runTest {
        val requestedPropertyId = "1"
        val johnSmithContactInfo = ContactInfo("John Smith", "+33123456789", "john.smith@agency.com")
        val propertyWithJohnSmithContactDomainModel = createRealEstate(id = requestedPropertyId, contactInfo = johnSmithContactInfo)
        val propertyWithJohnSmithContactDetailsUiModel = createRealEstateDetailsUiModel(
            id = requestedPropertyId,
            agentName = "John Smith",
            phoneNumber = "+33123456789",
            email = "john.smith@agency.com"
        )

        whenever(getRealEstateByIdUseCase.execute(requestedPropertyId))
            .thenReturn(propertyWithJohnSmithContactDomainModel)
        whenever(mapper.mapToScreenData(propertyWithJohnSmithContactDomainModel))
            .thenReturn(propertyWithJohnSmithContactDetailsUiModel)

        val emittedResults = interactor.handleIntent(DetailsIntent.LoadRealEstateDetails(requestedPropertyId)).toList()
        val expectedResults = listOf(
            DetailsResult.Loading,
            DetailsResult.Success(propertyWithJohnSmithContactDetailsUiModel)
        )

        assertEquals(expectedResults, emittedResults)
    }

    private fun createRealEstate(
        id: String = "1",
        title: String = "Test Villa",
        description: String = "Beautiful villa",
        price: Double = 1500000.0,
        currency: String = "EUR",
        location: String = "Nice",
        imageUrl: String = "villa.jpg",
        type: RealEstateType = RealEstateType.VILLA,
        bedrooms: Int = 4,
        bathrooms: Int = 3,
        area: Double = 250.0,
        isForSale: Boolean = true,
        contactInfo: ContactInfo = ContactInfo("Agent", "+123456789", "agent@test.com")
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

    private fun createRealEstateDetailsUiModel(
        id: String = "1",
        title: String = "Test Villa",
        description: String = "Beautiful villa",
        price: String = "€1,500,000",
        location: String = "Nice",
        imageUrl: String = "villa.jpg",
        type: String = "Villa",
        bedrooms: Int = 4,
        bathrooms: Int = 3,
        area: String = "250 m²",
        isForSale: Boolean = true,
        agentName: String = "Agent",
        phoneNumber: String = "+123456789",
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
}