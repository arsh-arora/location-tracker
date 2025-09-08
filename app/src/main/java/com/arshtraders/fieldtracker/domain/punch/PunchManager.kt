package com.arshtraders.fieldtracker.domain.punch

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.arshtraders.fieldtracker.data.database.dao.PunchDao
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao
import com.arshtraders.fieldtracker.data.database.entities.PunchEntity
import com.arshtraders.fieldtracker.data.database.entities.PlaceEntity
import com.arshtraders.fieldtracker.domain.location.GeocodingService
import com.arshtraders.fieldtracker.domain.location.AddressResult
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.*

@Singleton
class PunchManager @Inject constructor(
    private val fusedLocationClient: FusedLocationProviderClient,
    private val punchDao: PunchDao,
    private val placeDao: PlaceDao,
    private val geocodingService: GeocodingService
) {
    
    companion object {
        const val PLACE_DETECTION_RADIUS = 220.0 // meters
        const val MIN_ACCURACY_THRESHOLD = 150f // meters
    }
    
    suspend fun performPunch(type: PunchType): PunchResult {
        try {
            // Step 1: Get high-accuracy location
            val location = getHighAccuracyLocation()
            
            if (location.accuracy > MIN_ACCURACY_THRESHOLD) {
                return PunchResult.Error("Location accuracy too low: ${location.accuracy}m")
            }
            
            // Step 2: Find or create place
            val place = findOrCreatePlace(location)
            
            // Step 3: Create punch entity
            val punch = PunchEntity(
                userId = getUserId(),
                placeId = place.id,
                type = type.name,
                timestamp = System.currentTimeMillis(),
                latitude = location.latitude,
                longitude = location.longitude,
                accuracy = location.accuracy,
                serverDistance = calculateDistance(
                    location.latitude, location.longitude,
                    place.latitude, place.longitude
                ).toFloat()
            )
            
            // Step 4: Save punch
            punchDao.insertPunch(punch)
            
            Timber.d("Punch successful at ${place.name}")
            
            return PunchResult.Success(
                punchId = punch.id,
                placeId = place.id,
                placeName = place.name,
                serverDistance = punch.serverDistance ?: 0f
            )
            
        } catch (e: Exception) {
            Timber.e(e, "Failed to perform punch")
            return PunchResult.Error(e.message ?: "Unknown error")
        }
    }
    
    private suspend fun getHighAccuracyLocation(): android.location.Location {
        val cancellationToken = CancellationTokenSource()
        
        return fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            cancellationToken.token
        ).await() ?: throw Exception("Failed to get location")
    }
    
    private suspend fun findOrCreatePlace(location: android.location.Location): PlaceEntity {
        // Get all active places
        val activePlaces = placeDao.getActivePlaces()
        
        // Find nearest place within threshold
        val nearestPlace = activePlaces
            .map { place ->
                place to calculateDistance(
                    location.latitude, location.longitude,
                    place.latitude, place.longitude
                )
            }
            .filter { it.second <= PLACE_DETECTION_RADIUS }
            .minByOrNull { it.second }
            ?.first
        
        return nearestPlace ?: createNewPlace(location)
    }
    
    private suspend fun createNewPlace(location: android.location.Location): PlaceEntity {
        // Perform reverse geocoding to get human-readable address (with fallback)
        val addressResult = try {
            geocodingService.getAddressFromCoordinates(
                location.latitude,
                location.longitude
            )
        } catch (e: Exception) {
            Timber.w(e, "Geocoding failed, using coordinate fallback")
            AddressResult.UnknownError(
                coordinateString = String.format("%.6f, %.6f", location.latitude, location.longitude),
                error = e.message ?: "Unknown error"
            )
        }
        
        // Create place name based on geocoding result
        val placeName = when (addressResult) {
            is AddressResult.Success -> {
                // Use feature name if available (business name), otherwise use formatted address
                addressResult.featureName?.takeIf { it.isNotBlank() }
                    ?: addressResult.formattedAddress
            }
            else -> {
                // Fallback to coordinate-based name
                "Location at ${addressResult.coordinateString}"
            }
        }
        
        val place = PlaceEntity(
            id = UUID.randomUUID().toString(),
            name = placeName,
            latitude = location.latitude,
            longitude = location.longitude,
            radius = maxOf(120, (location.accuracy + 40).toInt()),
            status = "PENDING",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
            // Enhanced address fields
            formattedAddress = if (addressResult is AddressResult.Success) addressResult.formattedAddress else null,
            placeType = if (addressResult is AddressResult.Success) addressResult.placeType.name else null,
            locality = if (addressResult is AddressResult.Success) addressResult.locality else null,
            subLocality = if (addressResult is AddressResult.Success) addressResult.subLocality else null,
            thoroughfare = if (addressResult is AddressResult.Success) addressResult.thoroughfare else null,
            featureName = if (addressResult is AddressResult.Success) addressResult.featureName else null
        )
        
        placeDao.insertPlace(place)
        
        Timber.d("Created new place: ${place.name} (${addressResult.javaClass.simpleName})")
        
        return place
    }
    
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // meters
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        
        return earthRadius * c
    }
    
    private fun getUserId(): String = "user_123" // TODO: Get from preferences
}

enum class PunchType {
    MANUAL_IN,
    MANUAL_OUT,
    AUTO_IN,
    AUTO_OUT
}

sealed class PunchResult {
    data class Success(
        val punchId: String,
        val placeId: String,
        val placeName: String,
        val serverDistance: Float
    ) : PunchResult()
    
    data class Error(val message: String) : PunchResult()
}