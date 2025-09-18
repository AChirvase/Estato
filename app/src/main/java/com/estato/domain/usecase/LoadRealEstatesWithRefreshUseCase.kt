package com.estato.domain.usecase

import com.estato.domain.model.RealEstate
import com.estato.domain.repository.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LoadRealEstatesWithRefreshUseCase @Inject constructor(
    private val repository: RealEstateRepository
) : LoadRealEstatesWithRefreshUseCaseInterface {

    override fun execute(): Flow<List<RealEstate>> = flow {
        // First trigger refresh to ensure we have fresh data
        try {
            repository.refreshRealEstates()
        } catch (e: IOException) {
            Timber.w(e, "Failed to refresh data from network, continuing with cached data")
        } catch (e: HttpException) {
            Timber.w(e, "Failed to refresh data from server, continuing with cached data")
        }

        // Now emit the data from cache/database
        repository.getRealEstates().collect { realEstates ->
            emit(realEstates)
        }
    }
}
