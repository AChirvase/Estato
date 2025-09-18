package com.estato.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "real_estates")
data class RealEstateEntity(
    @PrimaryKey
    val id: Int,
    val city: String,
    val area: Double,
    val price: Double,
    val professional: String,
    val propertyType: String,
    val offerType: Int,
    val rooms: Int?,
    val bedrooms: Int?,
    val url: String?,
    val cachedAt: Long = System.currentTimeMillis()
)
