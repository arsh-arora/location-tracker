package com.arshtraders.fieldtracker.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.domain.auth.RoleManager
import com.arshtraders.fieldtracker.domain.auth.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val roleManager: RoleManager,
    private val tokenManager: TokenManager
) : ViewModel() {
    
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState: StateFlow<NavigationState> = _navigationState.asStateFlow()
    
    init {
        viewModelScope.launch {
            updateNavigationState()
        }
    }
    
    private fun updateNavigationState() {
        val currentRole = roleManager.getCurrentUserRole()
        val hasAdminAccess = roleManager.isTeamLeadOrAbove()
        val hasManagerAccess = roleManager.isManagerOrAbove()
        val isAdmin = roleManager.isAdmin()
        val isSuperAdmin = roleManager.isSuperAdmin()
        
        _navigationState.value = NavigationState(
            currentRole = currentRole,
            hasAdminAccess = hasAdminAccess,
            hasManagerAccess = hasManagerAccess,
            isAdmin = isAdmin,
            isSuperAdmin = isSuperAdmin,
            userName = getCurrentUserName()
        )
    }
    
    private fun getCurrentUserName(): String {
        return when (val authState = tokenManager.getCurrentAuthState()) {
            is com.arshtraders.fieldtracker.domain.auth.AuthState.Authenticated -> authState.userName
            else -> "User"
        }
    }
    
    fun refreshNavigationState() {
        updateNavigationState()
    }
}

data class NavigationState(
    val currentRole: String? = null,
    val hasAdminAccess: Boolean = false,
    val hasManagerAccess: Boolean = false,
    val isAdmin: Boolean = false,
    val isSuperAdmin: Boolean = false,
    val userName: String = "User"
)