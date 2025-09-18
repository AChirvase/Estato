package com.estato.di

import android.content.Context
import androidx.room.Room
import com.estato.data.local.dao.RealEstateDao
import com.estato.data.local.database.EstatoDatabase
import com.estato.data.remote.api.RealEstateApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit = Retrofit.Builder()
        .baseUrl("https://gsl-apps-technical-test.dignp.com/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideRealEstateApi(retrofit: Retrofit): RealEstateApi =
        retrofit.create(RealEstateApi::class.java)

    @Provides
    @Singleton
    fun provideEstatoDatabase(
        @ApplicationContext context: Context
    ): EstatoDatabase = Room.databaseBuilder(
        context,
        EstatoDatabase::class.java,
        EstatoDatabase.DATABASE_NAME
    ).build()

    @Provides
    fun provideRealEstateDao(database: EstatoDatabase): RealEstateDao =
        database.realEstateDao()
}