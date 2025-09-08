package com.arshtraders.fieldtracker.presentation.punch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.database.dao.PunchDao
import com.arshtraders.fieldtracker.data.database.dao.PunchWithPlace
import com.arshtraders.fieldtracker.domain.punch.PunchManager
import com.arshtraders.fieldtracker.domain.punch.PunchResult
import com.arshtraders.fieldtracker.domain.punch.PunchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PunchViewModel @Inject constructor(
    private val punchManager: PunchManager,
    private val punchDao: PunchDao
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<PunchUiState>(PunchUiState.Idle())
    val uiState: StateFlow<PunchUiState> = _uiState.asStateFlow()
    
    init {
        loadLastPunch()
    }
    
    fun performPunch(type: PunchType) {
        viewModelScope.launch {
            _uiState.value = PunchUiState.Loading
            
            when (val result = punchManager.performPunch(type)) {
                is PunchResult.Success -> {
                    _uiState.value = PunchUiState.Success(
                        punchId = result.punchId,
                        placeId = result.placeId,
                        placeName = result.placeName,
                        serverDistance = result.serverDistance
                    )
                }
                is PunchResult.Error -> {
                    _uiState.value = PunchUiState.Error(result.message)
                }
            }
        }
    }
    
    fun resetState() {
        loadLastPunch()
    }
    
    private fun loadLastPunch() {
        viewModelScope.launch {
            try {
                val lastPunchWithPlace = punchDao.getLastPunchWithPlace()
                _uiState.value = PunchUiState.Idle(
                    lastPunch = lastPunchWithPlace?.let { punch ->
                        PunchInfo(
                            id = punch.id,
                            placeName = punch.placeName ?: punch.placeAddress ?: "Unknown Location",
                            type = punch.type,
                            timestamp = punch.timestamp
                        )
                    }
                )
            } catch (e: Exception) {
                // Fall back to basic punch data if join fails
                try {
                    val lastPunch = punchDao.getLastPunch()
                    _uiState.value = PunchUiState.Idle(
                        lastPunch = lastPunch?.let {
                            PunchInfo(
                                id = it.id,
                                placeName = "Location ${it.placeId?.take(8) ?: "unknown"}...",
                                type = it.type,
                                timestamp = it.timestamp
                            )
                        }
                    )
                } catch (e2: Exception) {
                    // If all else fails, start with empty state
                    _uiState.value = PunchUiState.Idle(lastPunch = null)
                }
            }
        }
    }
}

sealed class PunchUiState {
    data class Idle(val lastPunch: PunchInfo? = null) : PunchUiState()
    object Loading : PunchUiState()
    data class Success(
        val punchId: String,
        val placeId: String,
        val placeName: String,
        val serverDistance: Float
    ) : PunchUiState()
    data class Error(val message: String) : PunchUiState()
}

data class PunchInfo(
    val id: String,
    val placeName: String,
    val type: String,
    val timestamp: Long
)