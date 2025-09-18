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
    fun `reduce with Loading result should update state to loading`() {
        // Given
        val currentState = ListingViewState()
        val result = ListingResult.Loading

        // When
        val newState = reducer.reduce(currentState, result)

        // Then
        assertTrue(newState.isLoading)
        assertNull(newState.errorMessage)
    }

    @Test
    fun `reduce with Success result should update state with real estates`() {
        // Given
        val currentState = ListingViewState(isLoading = true)
        val realEstates = listOf(
            RealEstateUiModel("1", "Test", "Location", "$100", "image.jpg", "House", 3, 2, "150 m²")
        )
        val result = ListingResult.Success(realEstates)

        // When
        val newState = reducer.reduce(currentState, result)

        // Then
        assertFalse(newState.isLoading)
        assertEquals(realEstates, newState.realEstates)
        assertNull(newState.errorMessage)
    }

    @Test
    fun `reduce with Error result should update state with error message`() {
        // Given
        val currentState = ListingViewState(isLoading = true)
        val errorMessage = "Network error"
        val result = ListingResult.Error(errorMessage)

        // When
        val newState = reducer.reduce(currentState, result)

        // Then
        assertFalse(newState.isLoading)
        assertEquals(errorMessage, newState.errorMessage)
    }
}