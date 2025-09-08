package com.arshtraders.fieldtracker.domain.location

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class GeocodingService @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    private val geocoder by lazy { Geocoder(context, Locale.getDefault()) }
    
    /**
     * Converts latitude and longitude coordinates to a human-readable address
     * Returns formatted address string or fallback coordinate string
     */
    suspend fun getAddressFromCoordinates(
        latitude: Double,
        longitude: Double
    ): AddressResult = withContext(Dispatchers.IO) {
        try {
            // Check if Geocoder service is available
            if (!Geocoder.isPresent()) {
                Timber.w("Geocoder service not available on this device")
                return@withContext AddressResult.Unavailable(
                    coordinateString = formatCoordinates(latitude, longitude)
                )
            }
            
            // Perform reverse geocoding with timeout
            val addresses = withTimeoutOrNull(5000L) { // 5 second timeout
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    // Use the new callback-based API for Android 13+
                    suspendCancellableCoroutine { continuation ->
                        try {
                            geocoder.getFromLocation(latitude, longitude, 1) { addressList ->
                                continuation.resume(addressList ?: emptyList())
                            }
                        } catch (e: Exception) {
                            Timber.w(e, "Geocoder callback error")
                            continuation.resume(emptyList())
                        }
                    }
                } else {
                    // Use the deprecated but still functional API for older versions
                    try {
                        @Suppress("DEPRECATION")
                        geocoder.getFromLocation(latitude, longitude, 1) ?: emptyList()
                    } catch (e: Exception) {
                        Timber.w(e, "Geocoder legacy API error")
                        emptyList()
                    }
                }
            }
            
            // If timeout occurred, addresses will be null
            if (addresses == null) {
                Timber.w("Geocoding timed out for coordinates: $latitude, $longitude")
                return@withContext AddressResult.NetworkError(
                    coordinateString = formatCoordinates(latitude, longitude),
                    error = "Geocoding timeout"
                )
            }
            
            if (addresses.isNullOrEmpty()) {
                Timber.w("No address found for coordinates: $latitude, $longitude")
                return@withContext AddressResult.NotFound(
                    coordinateString = formatCoordinates(latitude, longitude)
                )
            }
            
            val address = addresses[0]
            val formattedAddress = formatAddress(address)
            val placeType = determineePlaceType(address)
            
            Timber.d("Address found: $formattedAddress")
            
            AddressResult.Success(
                formattedAddress = formattedAddress,
                placeType = placeType,
                locality = address.locality,
                subLocality = address.subLocality,
                thoroughfare = address.thoroughfare,
                featureName = address.featureName,
                coordinateString = formatCoordinates(latitude, longitude)
            )
            
        } catch (e: IOException) {
            Timber.e(e, "Network error during reverse geocoding")
            AddressResult.NetworkError(
                coordinateString = formatCoordinates(latitude, longitude),
                error = e.message ?: "Network error"
            )
        } catch (e: IllegalArgumentException) {
            Timber.e(e, "Invalid coordinates: $latitude, $longitude")
            AddressResult.InvalidCoordinates(
                coordinateString = formatCoordinates(latitude, longitude),
                error = "Invalid coordinates"
            )
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during reverse geocoding")
            AddressResult.UnknownError(
                coordinateString = formatCoordinates(latitude, longitude),
                error = e.message ?: "Unknown error"
            )
        }
    }
    
    /**
     * Formats an Address object into a human-readable string
     */
    private fun formatAddress(address: Address): String {
        val parts = mutableListOf<String>()
        
        // Add feature name (business name, building name, etc.)
        address.featureName?.let { if (it.isNotBlank()) parts.add(it) }
        
        // Add thoroughfare (street address)
        address.thoroughfare?.let { if (it.isNotBlank()) parts.add(it) }
        
        // Add sub-locality (neighborhood)
        address.subLocality?.let { if (it.isNotBlank()) parts.add(it) }
        
        // Add locality (city)
        address.locality?.let { if (it.isNotBlank()) parts.add(it) }
        
        // Add admin area (state/province)
        address.adminArea?.let { if (it.isNotBlank()) parts.add(it) }
        
        // If we have no meaningful parts, use the full address line
        if (parts.isEmpty()) {
            return address.getAddressLine(0) ?: "Unknown Location"
        }
        
        return parts.joinToString(", ")
    }
    
    /**
     * Determines the type of place based on address components
     */
    private fun determineePlaceType(address: Address): PlaceType {
        return when {
            address.featureName != null -> PlaceType.BUSINESS
            address.thoroughfare != null -> PlaceType.STREET_ADDRESS
            address.locality != null -> PlaceType.LOCALITY
            else -> PlaceType.UNKNOWN
        }
    }
    
    /**
     * Formats coordinates as a fallback display string
     */
    private fun formatCoordinates(latitude: Double, longitude: Double): String {
        return String.format("%.6f, %.6f", latitude, longitude)
    }
}

/**
 * Sealed class representing the result of reverse geocoding operation
 */
sealed class AddressResult {
    abstract val coordinateString: String
    abstract fun getDisplayName(): String
    
    data class Success(
        val formattedAddress: String,
        val placeType: PlaceType,
        val locality: String?,
        val subLocality: String?,
        val thoroughfare: String?,
        val featureName: String?,
        override val coordinateString: String
    ) : AddressResult() {
        override fun getDisplayName(): String = formattedAddress
    }
    
    data class NotFound(
        override val coordinateString: String
    ) : AddressResult() {
        override fun getDisplayName(): String = "Unknown Location ($coordinateString)"
    }
    
    data class Unavailable(
        override val coordinateString: String
    ) : AddressResult() {
        override fun getDisplayName(): String = "Location Service Unavailable ($coordinateString)"
    }
    
    data class NetworkError(
        override val coordinateString: String,
        val error: String
    ) : AddressResult() {
        override fun getDisplayName(): String = "Location Lookup Failed ($coordinateString)"
    }
    
    data class InvalidCoordinates(
        override val coordinateString: String,
        val error: String
    ) : AddressResult() {
        override fun getDisplayName(): String = "Invalid Location ($coordinateString)"
    }
    
    data class UnknownError(
        override val coordinateString: String,
        val error: String
    ) : AddressResult() {
        override fun getDisplayName(): String = "Location Error ($coordinateString)"
    }
}

/**
 * Enum representing different types of places
 */
enum class PlaceType {
    BUSINESS,           // Restaurant, store, office building, etc.
    STREET_ADDRESS,     // Residential or specific street address  
    LOCALITY,           // City, town, or general area
    UNKNOWN             // Could not determine type
}