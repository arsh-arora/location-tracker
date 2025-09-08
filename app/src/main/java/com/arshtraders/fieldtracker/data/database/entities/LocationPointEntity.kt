package com.arshtraders.fieldtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "location_points")
data class LocationPointEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val deviceId: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val speed: Float? = null,
    val provider: String,
    val isMockLocation: Boolean = false,
    val batteryLevel: Int? = null,
    val isUploaded: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)