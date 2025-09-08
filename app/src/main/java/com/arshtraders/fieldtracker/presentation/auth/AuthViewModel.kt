package com.arshtraders.fieldtracker.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arshtraders.fieldtracker.data.network.dto.UserDto
import com.arshtraders.fieldtracker.domain.auth.AuthRepository
import com.arshtraders.fieldtracker.domain.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()
    
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _uiState.value = AuthUiState.Error("Please fill in all fields")
            return
        }
        
        if (!isValidEmail(email)) {
            _uiState.value = AuthUiState.Error("Please enter a valid email address")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            
            when (val result = authRepository.login(email, password)) {
                is AuthResult.Success -> {
                    _uiState.value = AuthUiState.Success(result.user)
                    Timber.d("Login successful: ${result.user.email}")
                }
                is AuthResult.Error -> {
                    _uiState.value = AuthUiState.Error(result.message)
                    Timber.w("Login failed: ${result.message}")
                }
            }
        }
    }
    
    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        name: String,
        phoneNumber: String? = null,
        employeeId: String? = null,
        department: String? = null
    ) {
        // Validation
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank() || name.isBlank()) {
            _uiState.value = AuthUiState.Error("Please fill in all required fields")
            return
        }
        
        if (!isValidEmail(email)) {
            _uiState.value = AuthUiState.Error("Please enter a valid email address")
            return
        }
        
        if (password.length < 6) {
            _uiState.value = AuthUiState.Error("Password must be at least 6 characters long")
            return
        }
        
        if (password != confirmPassword) {
            _uiState.value = AuthUiState.Error("Passwords do not match")
            return
        }
        
        if (name.length < 2) {
            _uiState.value = AuthUiState.Error("Name must be at least 2 characters long")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            
            when (val result = authRepository.register(
                email = email,
                password = password,
                name = name,
                phoneNumber = phoneNumber,
                employeeId = employeeId,
                department = department
            )) {
                is AuthResult.Success -> {
                    _uiState.value = AuthUiState.Success(result.user)
                    Timber.d("Registration successful: ${result.user.email}")
                }
                is AuthResult.Error -> {
                    _uiState.value = AuthUiState.Error(result.message)
                    Timber.w("Registration failed: ${result.message}")
                }
            }
        }
    }
    
    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val user: UserDto) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}