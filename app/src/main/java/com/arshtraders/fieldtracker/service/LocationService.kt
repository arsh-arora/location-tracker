package com.arshtraders.fieldtracker.service

import android.app.*
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import android.app.Service
import com.google.android.gms.location.*
import android.R
import com.arshtraders.fieldtracker.data.database.dao.LocationDao
import com.arshtraders.fieldtracker.data.database.entities.LocationPointEntity
import com.arshtraders.fieldtracker.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {
    
    @Inject lateinit var locationDao: LocationDao
    @Inject lateinit var fusedLocationClient: FusedLocationProviderClient
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var isTracking = false
    
    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val NOTIFICATION_CHANNEL_ID = "location_tracking_channel"
        const val NOTIFICATION_ID = 1001
        
        // Location parameters
        const val LOCATION_UPDATE_INTERVAL = 180_000L // 3 minutes
        const val FASTEST_LOCATION_INTERVAL = 60_000L // 1 minute
        const val MIN_DISPLACEMENT = 60f // 60 meters
    }
    
    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        LOCATION_UPDATE_INTERVAL
    ).apply {
        setMinUpdateDistanceMeters(MIN_DISPLACEMENT)
        setWaitForAccurateLocation(false)
        setMaxUpdateDelayMillis(LOCATION_UPDATE_INTERVAL * 2)
        setMinUpdateIntervalMillis(FASTEST_LOCATION_INTERVAL)
    }.build()
    
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            
            locationResult.locations.forEach { location ->
                Timber.d("New location: ${location.latitude}, ${location.longitude}")
                saveLocation(location)
            }
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        
        when (intent?.action) {
            ACTION_START -> startLocationTracking()
            ACTION_STOP -> stopLocationTracking()
        }
        
        return START_STICKY
    }
    
    private fun startLocationTracking() {
        if (isTracking) return
        
        Timber.d("Starting location tracking")
        isTracking = true
        
        // Start foreground service
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(NOTIFICATION_ID, createNotification(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(NOTIFICATION_ID, createNotification())
        }
        
        // Request location updates
        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
        } catch (e: SecurityException) {
            Timber.e(e, "Location permission not granted")
            stopSelf()
        }
    }
    
    private fun stopLocationTracking() {
        Timber.d("Stopping location tracking")
        isTracking = false
        
        fusedLocationClient.removeLocationUpdates(locationCallback)
        serviceScope.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    
    private fun saveLocation(location: android.location.Location) {
        serviceScope.launch {
            try {
                val locationEntity = LocationPointEntity(
                    userId = getUserId(),
                    deviceId = getDeviceIdentifier(),
                    timestamp = location.time,
                    latitude = location.latitude,
                    longitude = location.longitude,
                    accuracy = location.accuracy,
                    speed = if (location.hasSpeed()) location.speed else null,
                    provider = location.provider ?: "unknown",
                    isMockLocation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        location.isMock
                    } else {
                        location.isFromMockProvider
                    },
                    batteryLevel = getBatteryLevel()
                )
                
                locationDao.insertLocation(locationEntity)
                Timber.d("Location saved to database")
            } catch (e: Exception) {
                Timber.e(e, "Failed to save location")
            }
        }
    }
    
    private fun createNotification(): Notification {
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Location Tracking Active")
            .setContentText("Tracking your location in the background")
            .setSmallIcon(R.drawable.ic_menu_mylocation)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Location Tracking",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows when location tracking is active"
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun getUserId(): String = "user_123" // TODO: Get from preferences
    private fun getDeviceIdentifier(): String = "device_123" // TODO: Generate unique ID
    private fun getBatteryLevel(): Int = 100 // TODO: Get actual battery level
    
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}