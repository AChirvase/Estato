package com.estato.presentation.listing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ListingReducerTest {

    private lateinit var reducer: ListingReducer

    @BeforeEach
    fun setup() {
        reducer = ListingReducer()
    }

    @Test
    fun `given current state, when reduce with Loading result, then update state to loading`() {
        val initialState = ListingViewState()
        val loadingResult = ListingResult.Loading

        val updatedState = reducer.reduce(initialState, loadingResult)
        val expectedState = ListingViewState(
            isLoading = true,
            realEstates = initialState.realEstates,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given loading state, when reduce with Success result, then update state with real estates`() {
        val loadingState = ListingViewState(isLoading = true)
        val realEstatesList = listOf(
            createRealEstateUiModel("1", "Test Villa", "Nice", "€1,500,000", "villa.jpg", "Villa", 4, 3, "250 m²"),
            createRealEstateUiModel("2", "Test Apartment", "Paris", "€800,000", "apt.jpg", "Apartment", 2, 1, "100 m²")
        )
        val successResult = ListingResult.Success(realEstatesList)

        val updatedState = reducer.reduce(loadingState, successResult)
        val expectedState = ListingViewState(
            isLoading = false,
            realEstates = realEstatesList,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given loading state, when reduce with Error result, then update state with error message`() {
        val loadingState = ListingViewState(isLoading = true)
        val failureMessage = "Failed to load properties"
        val errorResult = ListingResult.Error(failureMessage)

        val updatedState = reducer.reduce(loadingState, errorResult)
        val expectedState = ListingViewState(
            isLoading = false,
            realEstates = loadingState.realEstates,
            errorMessage = failureMessage
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given state with error, when reduce with Loading, then clear previous error`() {
        val stateWithError = ListingViewState(errorMessage = "Previous error")
        val loadingResult = ListingResult.Loading

        val updatedState = reducer.reduce(stateWithError, loadingResult)
        val expectedState = ListingViewState(
            isLoading = true,
            realEstates = stateWithError.realEstates,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given state with error and loading, when reduce with Success, then clear error and loading state`() {
        val errorAndLoadingState = ListingViewState(
            isLoading = true,
            errorMessage = "Previous error"
        )
        val singleRealEstateList = listOf(
            createRealEstateUiModel("1", "Test Property", "Test City", "€100,000", "test.jpg", "Apartment", 2, 1, "100 m²")
        )
        val successResult = ListingResult.Success(singleRealEstateList)

        val updatedState = reducer.reduce(errorAndLoadingState, successResult)
        val expectedState = ListingViewState(
            isLoading = false,
            realEstates = singleRealEstateList,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given original state, when reduce with Loading, then maintain state immutability for original state`() {
        val originalRealEstatesList = listOf(
            createRealEstateUiModel("1", "Original Property", "Original City", "€200,000", "original.jpg", "House", 3, 2, "150 m²")
        )
        val originalState = ListingViewState(
            isLoading = false,
            realEstates = originalRealEstatesList,
            errorMessage = "Original error"
        )
        val loadingResult = ListingResult.Loading

        reducer.reduce(originalState, loadingResult)

        // Verify original state remains unchanged
        assertFalse(originalState.isLoading)
        assertEquals("Original error", originalState.errorMessage)
        assertEquals(originalRealEstatesList, originalState.realEstates)
    }

    @Test
    fun `given original state, when reduce with Loading, then create new state with updated values`() {
        val originalRealEstatesList = listOf(
            createRealEstateUiModel("1", "Original Property", "Original City", "€200,000", "original.jpg", "House", 3, 2, "150 m²")
        )
        val originalState = ListingViewState(
            isLoading = false,
            realEstates = originalRealEstatesList,
            errorMessage = "Original error"
        )
        val loadingResult = ListingResult.Loading

        val updatedState = reducer.reduce(originalState, loadingResult)

        assertTrue(updatedState.isLoading)
        assertNull(updatedState.errorMessage)
        assertEquals(originalRealEstatesList, updatedState.realEstates)
    }

    @Test
    fun `given loading state, when reduce with empty Success result, then handle empty list`() {
        val loadingState = ListingViewState(isLoading = true)
        val emptyRealEstatesList = emptyList<RealEstateUiModel>()
        val successWithEmptyListResult = ListingResult.Success(emptyRealEstatesList)

        val updatedState = reducer.reduce(loadingState, successWithEmptyListResult)
        val expectedState = ListingViewState(
            isLoading = false,
            realEstates = emptyRealEstatesList,
            errorMessage = null
        )

        assertEquals(expectedState, updatedState)
    }

    @Test
    fun `given state with real estates, when reduce with Error, then preserve existing real estates`() {
        val existingRealEstatesList = listOf(
            createRealEstateUiModel("1", "Existing Property", "Existing City", "€300,000", "existing.jpg", "Apartment", 2, 1, "80 m²")
        )
        val loadingStateWithExistingData = ListingViewState(
            isLoading = true,
            realEstates = existingRealEstatesList
        )
        val networkErrorResult = ListingResult.Error("Network error")

        val updatedState = reducer.reduce(loadingStateWithExistingData, networkErrorResult)
        val expectedState = ListingViewState(
            isLoading = false,
            realEstates = existingRealEstatesList,
            errorMessage = "Network error"
        )

        assertEquals(expectedState, updatedState)
    }

    private fun createRealEstateUiModel(
        id: String,
        title: String,
        location: String,
        price: String,
        imageUrl: String,
        type: String,
        bedrooms: Int,
        bathrooms: Int,
        area: String
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
}
