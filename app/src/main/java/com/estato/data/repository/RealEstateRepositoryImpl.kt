package com.estato.data.repository

import com.estato.data.local.dao.RealEstateDao
import com.estato.data.mapper.RealEstateMapper
import com.estato.data.remote.api.RealEstateApi
import com.estato.domain.model.RealEstate
import com.estato.domain.repository.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepositoryImpl @Inject constructor(
    private val api: RealEstateApi,
    private val dao: RealEstateDao,
    private val mapper: RealEstateMapper
) : RealEstateRepository {

    override fun getRealEstates(): Flow<List<RealEstate>> {
        return dao.getAllRealEstates().map { entities ->
            entities.map { mapper.mapToDomain(it) }
        }
    }

    override suspend fun getRealEstateById(id: String): RealEstate? {
        val idInt = id.toIntOrNull() ?: return null

        // First check cache
        val cachedEntity = dao.getRealEstateById(idInt)
        return cachedEntity?.let {
            Timber.d("Found real estate $id in cache")
            mapper.mapToDomain(it)
        } ?: fetchFromApi(idInt, id)
    }

    private suspend fun fetchFromApi(idInt: Int, id: String): RealEstate? {
        return try {
            Timber.d("Fetching real estate $id from API")
            val dto = api.getRealEstateById(idInt)
            val entity = mapper.mapToEntity(dto)
            dao.insertRealEstate(entity)
            mapper.mapToDomain(entity)
        } catch (e: IOException) {
            Timber.e(e, "Network error fetching real estate $id")
            null
        } catch (e: HttpException) {
            Timber.e(e, "HTTP error fetching real estate $id")
            null
        }
    }

    override suspend fun refreshRealEstates() {
        try {
            // Check if we already have cached data
            val cachedCount = dao.getCount()
            if (cachedCount > 0) {
                Timber.d("Real estates already cached ($cachedCount items), skipping API call")
                return
            }

            Timber.d("Fetching real estates from API")
            val response = api.getRealEstates()
            val entities = response.items.map { mapper.mapToEntity(it) }

            // Clear old data and insert new data
            dao.clearAllRealEstates()
            dao.insertRealEstates(entities)

            Timber.d("Cached ${entities.size} real estates")
        } catch (e: IOException) {
            Timber.e(e, "Network error refreshing real estates")
            handleRefreshFailure()
        } catch (e: HttpException) {
            Timber.e(e, "HTTP error refreshing real estates")
            handleRefreshFailure()
        }
    }

    private suspend fun handleRefreshFailure() {
        val cachedCount = dao.getCount()
        if (cachedCount == 0) {
            Timber.d("No cached data and API failed, using mock data")
            insertMockData()
        }
    }

    private suspend fun insertMockData() {
        val mockEntities = listOf(
            createMockApartment(),
            createMockVilla()
        )
        dao.insertRealEstates(mockEntities)
    }

    private fun createMockApartment() = mapper.mapToEntity(
        com.estato.data.remote.dto.RealEstateDto(
            id = 1,
            city = "Paris",
            area = 120.0,
            price = 850000.0,
            professional = "GSL EXPLORE",
            propertyType = "Appartement",
            offerType = 1,
            rooms = 4,
            bedrooms = 2,
            url = "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400"
        )
    )

    private fun createMockVilla() = mapper.mapToEntity(
        com.estato.data.remote.dto.RealEstateDto(
            id = 2,
            city = "Lyon",
            area = 250.0,
            price = 1200000.0,
            professional = "GSL PREMIUM",
            propertyType = "Maison - Villa",
            offerType = 1,
            rooms = 6,
            bedrooms = 4,
            url = "https://images.unsplash.com/photo-1613490493576-7fde63acd811?w=400"
        )
    )
}
