package com.estato.domain.usecase

import com.estato.domain.model.ContactInfo
import com.estato.domain.model.RealEstate
import com.estato.domain.model.RealEstateType
import com.estato.domain.repository.RealEstateRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRealEstatesUseCaseTest {

    private lateinit var repository: RealEstateRepository
    private lateinit var useCase: GetRealEstatesUseCase

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GetRealEstatesUseCase(repository)
    }

    @Test
    fun `invoke should return real estates from repository`() = runTest {
        // Given
        val realEstates = listOf(
            RealEstate(
                id = "1",
                title = "Test House",
                description = "A test house",
                price = 100000.0,
                currency = "USD",
                location = "Test Location",
                imageUrl = "test.jpg",
                type = RealEstateType.HOUSE,
                bedrooms = 3,
                bathrooms = 2,
                area = 150.0,
                isForSale = true,
                contactInfo = ContactInfo("Agent", "123", "test@test.com")
            )
        )
        coEvery { repository.getRealEstates() } returns flowOf(realEstates)

        // When
        val result = useCase.execute().toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(realEstates, result[0])
    }
}
