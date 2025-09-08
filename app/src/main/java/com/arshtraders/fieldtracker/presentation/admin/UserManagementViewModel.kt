package com.arshtraders.fieldtracker.presentation.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.network.dto.CreateUserRequestDto
import com.arshtraders.fieldtracker.data.network.dto.UpdateUserRequestDto
import com.arshtraders.fieldtracker.data.network.dto.UserManagementDto
import com.arshtraders.fieldtracker.domain.admin.AdminRepository
import com.arshtraders.fieldtracker.domain.admin.AdminResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserManagementViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UserManagementUiState>(UserManagementUiState.Loading)
    val uiState: StateFlow<UserManagementUiState> = _uiState.asStateFlow()
    
    private var allUsers = listOf<UserManagementDto>()
    private var currentSearchQuery = ""

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _uiState.value = UserManagementUiState.Loading
                
                when (val result = adminRepository.getAllUsers()) {
                    is AdminResult.Success -> {
                        allUsers = result.data
                        applyFilter()
                    }
                    is AdminResult.Error -> {
                        _uiState.value = UserManagementUiState.Error(result.message)
                        Timber.e("Failed to load users: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to load users: ${e.message}"
                _uiState.value = UserManagementUiState.Error(errorMessage)
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun searchUsers(query: String) {
        currentSearchQuery = query
        applyFilter()
    }
    
    private fun applyFilter() {
        val filteredUsers = if (currentSearchQuery.isBlank()) {
            allUsers
        } else {
            allUsers.filter { user ->
                user.name.contains(currentSearchQuery, ignoreCase = true) ||
                user.email.contains(currentSearchQuery, ignoreCase = true) ||
                user.employeeId?.contains(currentSearchQuery, ignoreCase = true) == true ||
                user.department?.contains(currentSearchQuery, ignoreCase = true) == true
            }
        }
        
        _uiState.value = UserManagementUiState.Success(
            users = filteredUsers,
            selectedUser = (_uiState.value as? UserManagementUiState.Success)?.selectedUser
        )
    }
    
    fun addUser(userRequest: CreateUserRequestDto) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.createUser(userRequest)) {
                    is AdminResult.Success -> {
                        loadUsers() // Reload to get updated list
                        Timber.d("User created successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is UserManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = UserManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to create user: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to create user: ${e.message}"
                val currentState = _uiState.value
                if (currentState is UserManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = UserManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun updateUser(userId: String, userRequest: UpdateUserRequestDto) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.updateUser(userId, userRequest)) {
                    is AdminResult.Success -> {
                        loadUsers() // Reload to get updated list
                        Timber.d("User updated successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is UserManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = UserManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to update user: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to update user: ${e.message}"
                val currentState = _uiState.value
                if (currentState is UserManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = UserManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun deleteUser(userId: String) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.deleteUser(userId)) {
                    is AdminResult.Success -> {
                        loadUsers() // Reload to get updated list
                        Timber.d("User deleted successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is UserManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = UserManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to delete user: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to delete user: ${e.message}"
                val currentState = _uiState.value
                if (currentState is UserManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = UserManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun toggleUserActiveStatus(userId: String, isActive: Boolean) {
        viewModelScope.launch {
            try {
                when (val result = adminRepository.updateUserStatus(userId, isActive)) {
                    is AdminResult.Success -> {
                        loadUsers() // Reload to get updated list
                        Timber.d("User status updated successfully")
                    }
                    is AdminResult.Error -> {
                        val currentState = _uiState.value
                        if (currentState is UserManagementUiState.Success) {
                            _uiState.value = currentState.copy(errorMessage = result.message)
                        } else {
                            _uiState.value = UserManagementUiState.Error(result.message)
                        }
                        Timber.e("Failed to update user status: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                val errorMessage = "Failed to update user status: ${e.message}"
                val currentState = _uiState.value
                if (currentState is UserManagementUiState.Success) {
                    _uiState.value = currentState.copy(errorMessage = errorMessage)
                } else {
                    _uiState.value = UserManagementUiState.Error(errorMessage)
                }
                Timber.e(e, errorMessage)
            }
        }
    }
    
    fun selectUserForEdit(user: UserManagementDto) {
        val currentState = _uiState.value
        if (currentState is UserManagementUiState.Success) {
            _uiState.value = currentState.copy(selectedUser = user)
        }
    }
    
    fun clearSelectedUser() {
        val currentState = _uiState.value
        if (currentState is UserManagementUiState.Success) {
            _uiState.value = currentState.copy(selectedUser = null)
        }
    }
    
    fun clearError() {
        val currentState = _uiState.value
        if (currentState is UserManagementUiState.Success) {
            _uiState.value = currentState.copy(errorMessage = null)
        }
    }
}

sealed class UserManagementUiState {
    object Loading : UserManagementUiState()
    data class Error(val message: String) : UserManagementUiState()
    data class Success(
        val users: List<UserManagementDto>,
        val selectedUser: UserManagementDto? = null,
        val errorMessage: String? = null
    ) : UserManagementUiState()
}