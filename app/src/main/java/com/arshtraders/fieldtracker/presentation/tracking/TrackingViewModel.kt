package com.arshtraders.fieldtracker.presentation.tracking

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.database.dao.LocationDao
import com.arshtraders.fieldtracker.service.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    application: Application,
    private val locationDao: LocationDao
) : AndroidViewModel(application) {
    
    private val _uiState = MutableStateFlow(TrackingUiState())
    val uiState: StateFlow<TrackingUiState> = _uiState.asStateFlow()
    
    init {
        observeLatestLocation()
    }
    
    fun toggleTracking() {
        val context = getApplication<Application>()
        val intent = Intent(context, LocationService::class.java)
        
        if (_uiState.value.isTracking) {
            intent.action = LocationService.ACTION_STOP
            context.startService(intent)
            _uiState.update { it.copy(isTracking = false) }
        } else {
            intent.action = LocationService.ACTION_START
            context.startForegroundService(intent)
            _uiState.update { it.copy(isTracking = true) }
        }
    }
    
    private fun observeLatestLocation() {
        viewModelScope.launch {
            locationDao.getLocationsAfter(System.currentTimeMillis() - 3600000)
                .collect { locations ->
                    locations.firstOrNull()?.let { location ->
                        _uiState.update { state ->
                            state.copy(
                                lastLocation = LocationInfo(
                                    latitude = location.latitude,
                                    longitude = location.longitude,
                                    accuracy = location.accuracy,
                                    timestamp = location.timestamp
                                )
                            )
                        }
                    }
                }
        }
    }
}

data class TrackingUiState(
    val isTracking: Boolean = false,
    val lastLocation: LocationInfo? = null,
    val error: String? = null
)

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val timestamp: Long
)