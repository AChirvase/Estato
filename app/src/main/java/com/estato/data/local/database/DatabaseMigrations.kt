package com.estato.data.local.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // The rooms column already exists as NOT NULL, we need to make it nullable
            // SQLite doesn't support ALTER COLUMN, so we need to recreate the table

            // Create new table with nullable rooms
            db.execSQL("""
                CREATE TABLE real_estates_new (
                    id INTEGER NOT NULL PRIMARY KEY,
                    city TEXT NOT NULL,
                    area REAL NOT NULL,
                    price REAL NOT NULL,
                    professional TEXT NOT NULL,
                    propertyType TEXT NOT NULL,
                    offerType INTEGER NOT NULL,
                    rooms INTEGER,
                    bedrooms INTEGER,
                    url TEXT,
                    cachedAt INTEGER NOT NULL
                )
            """)

            // Copy data from old table to new table
            db.execSQL("""
                INSERT INTO real_estates_new
                SELECT id, city, area, price, professional, propertyType, offerType,
                       rooms, bedrooms, url, cachedAt
                FROM real_estates
            """)

            // Drop old table
            db.execSQL("DROP TABLE real_estates")

            // Rename new table
            db.execSQL("ALTER TABLE real_estates_new RENAME TO real_estates")
        }
    }
}
