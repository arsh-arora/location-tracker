package com.arshtraders.fieldtracker.domain.auth

import android.content.Context
import com.arshtraders.fieldtracker.data.database.dao.UserDao
import com.arshtraders.fieldtracker.data.database.entities.UserEntity
import com.arshtraders.fieldtracker.data.network.dto.*
import com.arshtraders.fieldtracker.data.supabase.SupabaseAuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val supabaseAuthRepository: SupabaseAuthRepository,
    private val tokenManager: TokenManager,
    private val userDao: UserDao,
    @ApplicationContext private val context: Context
) {
    
    suspend fun login(email: String, password: String): AuthResult = withContext(Dispatchers.IO) {
        try {
            // Use Supabase for authentication
            val result = supabaseAuthRepository.signIn(email, password)
            
            if (result is AuthResult.Success) {
                val user = result.user
                
                // Save tokens (for compatibility with existing TokenManager)
                tokenManager.saveAuthData(
                    accessToken = "supabase_session", // Placeholder - Supabase handles sessions internally
                    refreshToken = "supabase_refresh", // Placeholder 
                    expiresAt = System.currentTimeMillis() + (24 * 60 * 60 * 1000), // 24 hours
                    userId = user.id,
                    userEmail = user.email,
                    userName = user.name,
                    userRole = user.role
                )
                
                // Save user to local database
                val userEntity = UserEntity(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    role = user.role,
                    profilePictureUrl = user.profilePictureUrl,
                    phoneNumber = user.phoneNumber,
                    department = user.department,
                    employeeId = user.employeeId,
                    isActive = user.isActive,
                    createdAt = user.createdAt,
                    updatedAt = user.updatedAt,
                    lastLoginAt = System.currentTimeMillis()
                )
                userDao.insertUser(userEntity)
                
                Timber.d("Login successful for user: ${user.email}")
            }
            
            result
        } catch (e: Exception) {
            Timber.e(e, "Login error")
            AuthResult.Error("Network error. Please check your connection")
        }
    }
    
    suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String? = null,
        employeeId: String? = null,
        department: String? = null
    ): AuthResult = withContext(Dispatchers.IO) {
        try {
            // Use Supabase for registration
            val result = supabaseAuthRepository.signUp(
                email = email,
                password = password,
                name = name,
                phoneNumber = phoneNumber,
                employeeId = employeeId,
                department = department
            )
            
            if (result is AuthResult.Success) {
                val user = result.user
                
                // Save tokens (for compatibility with existing TokenManager)
                tokenManager.saveAuthData(
                    accessToken = "supabase_session", // Placeholder - Supabase handles sessions internally
                    refreshToken = "supabase_refresh", // Placeholder
                    expiresAt = System.currentTimeMillis() + (24 * 60 * 60 * 1000), // 24 hours
                    userId = user.id,
                    userEmail = user.email,
                    userName = user.name,
                    userRole = user.role
                )
                
                // Save user to local database
                val userEntity = UserEntity(
                    id = user.id,
                    email = user.email,
                    name = user.name,
                    role = user.role,
                    profilePictureUrl = user.profilePictureUrl,
                    phoneNumber = user.phoneNumber,
                    department = user.department,
                    employeeId = user.employeeId,
                    isActive = user.isActive,
                    createdAt = user.createdAt,
                    updatedAt = user.updatedAt,
                    lastLoginAt = System.currentTimeMillis()
                )
                userDao.insertUser(userEntity)
                
                Timber.d("Registration successful for user: ${user.email}")
            }
            
            result
        } catch (e: Exception) {
            Timber.e(e, "Registration error")
            AuthResult.Error("Network error. Please check your connection")
        }
    }
    
    suspend fun refreshToken(): Boolean = withContext(Dispatchers.IO) {
        try {
            // Use Supabase for token refresh
            val refreshed = supabaseAuthRepository.refreshSession()
            
            if (refreshed) {
                // Update token expiry time for compatibility
                tokenManager.updateAccessToken(
                    accessToken = "supabase_session",
                    expiresAt = System.currentTimeMillis() + (24 * 60 * 60 * 1000) // 24 hours
                )
                Timber.d("Token refreshed successfully")
            } else {
                Timber.w("Token refresh failed")
            }
            
            refreshed
        } catch (e: Exception) {
            Timber.e(e, "Token refresh error")
            false
        }
    }
    
    suspend fun logout(): Boolean = withContext(Dispatchers.IO) {
        try {
            // Use Supabase for logout
            val loggedOut = supabaseAuthRepository.signOut()
            
            if (loggedOut) {
                // Clear local auth data
                tokenManager.clearAuthData()
                Timber.d("Logout successful")
            } else {
                Timber.w("Logout failed but clearing local data anyway")
                tokenManager.clearAuthData()
            }
            
            true // Always return true to clear local state
        } catch (e: Exception) {
            Timber.e(e, "Logout error")
            // Clear local data even if remote logout fails
            tokenManager.clearAuthData()
            true
        }
    }
    
    suspend fun getCurrentUser(): UserEntity? {
        val userId = tokenManager.getCurrentUserId()
        return if (userId != null) {
            userDao.getUserById(userId)
        } else {
            null
        }
    }
    
}

sealed class AuthResult {
    data class Success(val user: UserDto) : AuthResult()
    data class Error(val message: String) : AuthResult()
}