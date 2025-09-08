package com.arshtraders.fieldtracker.data.supabase

import com.arshtraders.fieldtracker.data.network.dto.UserDto
import com.arshtraders.fieldtracker.domain.auth.AuthResult
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabaseAuthRepository @Inject constructor(
    private val supabaseClient: SupabaseClient
) {
    
    suspend fun signUp(
        email: String, 
        password: String, 
        name: String,
        phoneNumber: String? = null,
        employeeId: String? = null,
        department: String? = null
    ): AuthResult = withContext(Dispatchers.IO) {
        try {
            // Sign up with Supabase Auth
            val result = supabaseClient.auth.signUpWith(Email) {
                this.email = email.trim().lowercase()
                this.password = password
                // Use JsonObject for user metadata
                this.data = buildJsonObject {
                    put("name", name.trim())
                    phoneNumber?.trim()?.takeIf { it.isNotEmpty() }?.let { put("phone_number", it) }
                    employeeId?.trim()?.takeIf { it.isNotEmpty() }?.let { put("employee_id", it) }
                    department?.trim()?.takeIf { it.isNotEmpty() }?.let { put("department", it) }
                }
            }
            
            // Check if signup was successful (user might need email confirmation)
            val authUser = supabaseClient.auth.currentUserOrNull()
            if (authUser != null) {
                // User is immediately signed in (email confirmation disabled)
                val userDto = UserDto(
                    id = authUser.id,
                    email = authUser.email ?: email,
                    name = name,
                    role = "USER", // Default role
                    profilePictureUrl = null,
                    phoneNumber = phoneNumber,
                    department = department,
                    employeeId = employeeId,
                    isActive = true,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                
                Timber.d("Sign up successful for user: $email")
                AuthResult.Success(userDto)
            } else {
                // Registration successful but needs email confirmation
                Timber.d("Sign up successful but email confirmation required for: $email")
                AuthResult.Error("Registration successful! Please check your email to confirm your account.")
            }
        } catch (e: Exception) {
            Timber.e(e, "Sign up error: ${e.message}")
            val errorMessage = when {
                e.message?.contains("already registered") == true -> "Email already exists"
                e.message?.contains("invalid email") == true -> "Invalid email address"
                e.message?.contains("weak password") == true -> "Password is too weak"
                e.message?.contains("email") == true -> "Email confirmation required - check your email"
                else -> "Registration failed: ${e.message}"
            }
            AuthResult.Error(errorMessage)
        }
    }
    
    suspend fun signIn(email: String, password: String): AuthResult = withContext(Dispatchers.IO) {
        try {
            // Sign in with Supabase Auth
            supabaseClient.auth.signInWith(Email) {
                this.email = email.trim().lowercase()
                this.password = password
            }
            
            val currentUser = supabaseClient.auth.currentUserOrNull()
            if (currentUser != null) {
                // Fetch user profile from profiles table
                val profiles = supabaseClient.from("profiles")
                val profile = profiles.select() {
                    filter {
                        eq("id", currentUser.id)
                    }
                }.decodeSingle<Profile>()
                
                // Convert to UserDto for consistency
                val userDto = UserDto(
                    id = profile.id,
                    email = profile.email,
                    name = profile.name,
                    role = profile.role ?: "USER",
                    profilePictureUrl = profile.profilePictureUrl,
                    phoneNumber = profile.phoneNumber,
                    department = profile.department,
                    employeeId = profile.employeeId,
                    isActive = profile.isActive,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                
                Timber.d("Sign in successful for user: ${currentUser.email}")
                AuthResult.Success(userDto)
            } else {
                AuthResult.Error("Sign in failed - no user session")
            }
        } catch (e: Exception) {
            Timber.e(e, "Sign in error: ${e.message}")
            val errorMessage = when {
                e.message?.contains("invalid credentials") == true -> "Invalid email or password"
                e.message?.contains("email not confirmed") == true -> "Please confirm your email address"
                e.message?.contains("too many requests") == true -> "Too many login attempts. Please try again later"
                else -> "Login failed: ${e.message}"
            }
            AuthResult.Error(errorMessage)
        }
    }
    
    suspend fun signOut(): Boolean = withContext(Dispatchers.IO) {
        try {
            supabaseClient.auth.signOut()
            Timber.d("Sign out successful")
            true
        } catch (e: Exception) {
            Timber.e(e, "Sign out error")
            false
        }
    }
    
    fun getCurrentUserId(): String? {
        return supabaseClient.auth.currentUserOrNull()?.id
    }
    
    fun isLoggedIn(): Boolean {
        return supabaseClient.auth.currentSessionOrNull() != null
    }
    
    suspend fun refreshSession(): Boolean = withContext(Dispatchers.IO) {
        try {
            supabaseClient.auth.refreshCurrentSession()
            true
        } catch (e: Exception) {
            Timber.e(e, "Session refresh error")
            false
        }
    }
}