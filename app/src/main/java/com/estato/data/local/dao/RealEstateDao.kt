package com.estato.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estato.data.local.entity.RealEstateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Query("SELECT * FROM real_estates ORDER BY id ASC")
    fun getAllRealEstates(): Flow<List<RealEstateEntity>>

    @Query("SELECT * FROM real_estates WHERE id = :id LIMIT 1")
    suspend fun getRealEstateById(id: Int): RealEstateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRealEstates(realEstates: List<RealEstateEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRealEstate(realEstate: RealEstateEntity)

    @Query("DELETE FROM real_estates")
    suspend fun clearAllRealEstates()

    @Query("SELECT COUNT(*) FROM real_estates")
    suspend fun getCount(): Int
}
