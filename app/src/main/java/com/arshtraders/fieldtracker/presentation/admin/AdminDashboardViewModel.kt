package com.arshtraders.fieldtracker.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.network.dto.DashboardStatsDto
import com.arshtraders.fieldtracker.domain.admin.AdminRepository
import com.arshtraders.fieldtracker.domain.admin.AdminResult
import com.arshtraders.fieldtracker.domain.auth.RoleManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdminDashboardViewModel @Inject constructor(
    private val adminRepository: AdminRepository,
    private val roleManager: RoleManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<AdminDashboardUiState>(AdminDashboardUiState.Loading)
    val uiState: StateFlow<AdminDashboardUiState> = _uiState.asStateFlow()
    
    init {
        // Check if user has access to admin dashboard
        if (!roleManager.hasPermission(RoleManager.PERM_VIEW_DASHBOARD)) {
            _uiState.value = AdminDashboardUiState.Error("Access denied: Insufficient permissions")
        }
    }
    
    fun loadDashboardData() {
        if (!roleManager.hasPermission(RoleManager.PERM_VIEW_DASHBOARD)) {
            _uiState.value = AdminDashboardUiState.Error("Access denied: Insufficient permissions")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = AdminDashboardUiState.Loading
            
            when (val result = adminRepository.getDashboardStats()) {
                is AdminResult.Success -> {
                    _uiState.value = AdminDashboardUiState.Success(result.data)
                    Timber.d("Dashboard data loaded successfully")
                }
                is AdminResult.Error -> {
                    _uiState.value = AdminDashboardUiState.Error(result.message)
                    Timber.e("Failed to load dashboard data: ${result.message}")
                }
            }
        }
    }
    
    fun refreshData() {
        loadDashboardData()
    }
}

sealed class AdminDashboardUiState {
    object Loading : AdminDashboardUiState()
    data class Success(val stats: DashboardStatsDto) : AdminDashboardUiState()
    data class Error(val message: String) : AdminDashboardUiState()
}