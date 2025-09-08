package com.arshtraders.fieldtracker.data.network.dto

data class LocationBatchRequest(
    val userId: String,
    val deviceId: String,
    val points: List<LocationPoint>
) {
    data class LocationPoint(
        val timestamp: Long,
        val latitude: Double,
        val longitude: Double,
        val accuracy: Float,
        val speed: Float?,
        val provider: String,
        val isMockLocation: Boolean
    )
}

data class LocationBatchResponse(
    val success: Boolean,
    val processed: Int,
    val rejected: Int
)

data class PunchUploadRequest(
    val userId: String,
    val placeId: String?,
    val type: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float
)

data class PunchUploadResponse(
    val punchId: String,
    val placeId: String,
    val serverDistance: Float
)

data class PlaceDto(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Int,
    val status: String
)