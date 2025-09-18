package com.estato.di

import com.estato.domain.usecase.GetRealEstateByIdUseCase
import com.estato.domain.usecase.GetRealEstateByIdUseCaseInterface
import com.estato.domain.usecase.GetRealEstatesUseCase
import com.estato.domain.usecase.GetRealEstatesUseCaseInterface
import com.estato.domain.usecase.LoadRealEstatesWithRefreshUseCase
import com.estato.domain.usecase.LoadRealEstatesWithRefreshUseCaseInterface
import com.estato.domain.usecase.RefreshRealEstatesUseCase
import com.estato.domain.usecase.RefreshRealEstatesUseCaseInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindGetRealEstatesUseCase(
        getRealEstatesUseCase: GetRealEstatesUseCase
    ): GetRealEstatesUseCaseInterface

    @Binds
    abstract fun bindGetRealEstateByIdUseCase(
        getRealEstateByIdUseCase: GetRealEstateByIdUseCase
    ): GetRealEstateByIdUseCaseInterface

    @Binds
    abstract fun bindRefreshRealEstatesUseCase(
        refreshRealEstatesUseCase: RefreshRealEstatesUseCase
    ): RefreshRealEstatesUseCaseInterface

    @Binds
    abstract fun bindLoadRealEstatesWithRefreshUseCase(
        loadRealEstatesWithRefreshUseCase: LoadRealEstatesWithRefreshUseCase
    ): LoadRealEstatesWithRefreshUseCaseInterface
}
