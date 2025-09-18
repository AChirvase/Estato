package com.estato.di

import com.estato.data.repository.RealEstateRepositoryImpl
import com.estato.domain.repository.RealEstateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRealEstateRepository(
        realEstateRepositoryImpl: RealEstateRepositoryImpl
    ): RealEstateRepository
}