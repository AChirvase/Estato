package com.estato.presentation.details

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DetailsReducerTest {

    private lateinit var reducer: DetailsReducer

    @BeforeEach
    fun setup() {
        reducer = DetailsReducer()
    }

    @Test
    fun `given current state, when reduce with Loading result, then update state to loading`() {
        val initialState = DetailsViewState()
        val loadingResult = DetailsResult.Loading

        val updatedState = reducer.reduce(initialState, loadingResult)
        val expectedState = DetailsViewState(
            isLoading = true,
            realEstate = initialState.realEstate,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given loading state, when reduce with Success result, then update state with real estate details`() {
        val loadingState = DetailsViewState(isLoading = true)
        val niceVillaDetailsUiModel = createRealEstateDetailsUiModel(
            id = "1",
            title = "Test Villa",
            description = "Beautiful villa",
            price = "€1,500,000",
            location = "Nice",
            imageUrl = "villa.jpg",
            type = "Villa",
            bedrooms = 4,
            bathrooms = 3,
            area = "250 m²",
            isForSale = true,
            agentName = "John Doe",
            phoneNumber = "+33123456789",
            email = "john@test.com"
        )
        val successResult = DetailsResult.Success(niceVillaDetailsUiModel)

        val updatedState = reducer.reduce(loadingState, successResult)
        val expectedState = DetailsViewState(
            isLoading = false,
            realEstate = niceVillaDetailsUiModel,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given loading state, when reduce with Error result, then update state with error message`() {
        val loadingState = DetailsViewState(isLoading = true)
        val failureMessage = "Failed to load property details"
        val errorResult = DetailsResult.Error(failureMessage)

        val updatedState = reducer.reduce(loadingState, errorResult)
        val expectedState = DetailsViewState(
            isLoading = false,
            realEstate = loadingState.realEstate,
            errorMessage = failureMessage
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given state with error, when reduce with Loading, then clear previous error`() {
        val stateWithError = DetailsViewState(errorMessage = "Previous error")
        val loadingResult = DetailsResult.Loading

        val updatedState = reducer.reduce(stateWithError, loadingResult)
        val expectedState = DetailsViewState(
            isLoading = true,
            realEstate = stateWithError.realEstate,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given state with error and loading, when reduce with Success, then clear error and loading state`() {
        val errorAndLoadingState = DetailsViewState(
            isLoading = true,
            errorMessage = "Previous error"
        )
        val testPropertyDetailsUiModel = createRealEstateDetailsUiModel()
        val successResult = DetailsResult.Success(testPropertyDetailsUiModel)

        val updatedState = reducer.reduce(errorAndLoadingState, successResult)
        val expectedState = DetailsViewState(
            isLoading = false,
            realEstate = testPropertyDetailsUiModel,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given original state, when reduce with Loading, then maintain state immutability for original state`() {
        val originalState = DetailsViewState(
            isLoading = false,
            realEstate = null,
            errorMessage = "Original error"
        )
        val loadingResult = DetailsResult.Loading

        reducer.reduce(originalState, loadingResult)

        // Verify original state remains unchanged
        assertFalse(originalState.isLoading)
        assertEquals("Original error", originalState.errorMessage)
    }

    @Test
    fun `given original state, when reduce with Loading, then create new state with updated values`() {
        val originalState = DetailsViewState(
            isLoading = false,
            realEstate = null,
            errorMessage = "Original error"
        )
        val loadingResult = DetailsResult.Loading

        val updatedState = reducer.reduce(originalState, loadingResult)

        assertTrue(updatedState.isLoading)
        assertNull(updatedState.errorMessage)
    }

    private fun createRealEstateDetailsUiModel(
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