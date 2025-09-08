package com.arshtraders.fieldtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "punches")
data class PunchEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val userId: String,
    val placeId: String? = null,
    val type: String, // MANUAL_IN, MANUAL_OUT, AUTO_IN, AUTO_OUT
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val serverDistance: Float? = null,
    val evidence: String? = null, // JSON string
    val isUploaded: Boolean = false,
    val syncStatus: String = "PENDING", // PENDING, SYNCED, FAILED
    val createdAt: Long = System.currentTimeMillis()
)