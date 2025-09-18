package com.estato.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estato.data.local.dao.RealEstateDao
import com.estato.data.local.entity.RealEstateEntity

@Database(
    entities = [RealEstateEntity::class],
    version = 2,
    exportSchema = false
)
abstract class EstatoDatabase : RoomDatabase() {

    abstract fun realEstateDao(): RealEstateDao

    companion object {
        const val DATABASE_NAME = "estato_database"
    }
}
