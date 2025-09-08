package com.arshtraders.fieldtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val status: String, // PENDING, ACTIVE, REJECTED, MERGED
    val createdAt: Long,
    val updatedAt: Long,
    // Enhanced address fields for reverse geocoding
    val formattedAddress: String? = null,
    val placeType: String? = null, // BUSINESS, STREET_ADDRESS, LOCALITY, UNKNOWN
    val locality: String? = null, // City
    val subLocality: String? = null, // Neighborhood  
    val thoroughfare: String? = null, // Street address
    val featureName: String? = null // Business name, building name
)