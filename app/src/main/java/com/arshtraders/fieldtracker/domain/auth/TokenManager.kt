package com.arshtraders.fieldtracker.domain.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "field_tracker_secure_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_TOKEN_EXPIRES_AT = "token_expires_at"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_ROLE = "user_role"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: Flow<AuthState> = _authState.asStateFlow()

    private val encryptedPrefs: SharedPreferences by lazy {
        try {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            EncryptedSharedPreferences.create(
                context,
                PREFS_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to create encrypted shared preferences, falling back to regular SharedPreferences")
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        }
    }

    init {
        checkAuthState()
    }

    fun saveAuthData(
        accessToken: String,
        refreshToken: String,
        expiresAt: Long,
        userId: String,
        userEmail: String,
        userName: String,
        userRole: String
    ) {
        try {
            encryptedPrefs.edit()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .putString(KEY_REFRESH_TOKEN, refreshToken)
                .putLong(KEY_TOKEN_EXPIRES_AT, expiresAt)
                .putString(KEY_USER_ID, userId)
                .putString(KEY_USER_EMAIL, userEmail)
                .putString(KEY_USER_NAME, userName)
                .putString(KEY_USER_ROLE, userRole)
                .putBoolean(KEY_IS_LOGGED_IN, true)
                .apply()

            _authState.value = AuthState.Authenticated(
                userId = userId,
                userEmail = userEmail,
                userName = userName,
                userRole = userRole
            )

            Timber.d("Auth data saved successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to save auth data")
        }
    }

    fun getAccessToken(): String? {
        return try {
            encryptedPrefs.getString(KEY_ACCESS_TOKEN, null)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get access token")
            null
        }
    }

    fun getRefreshToken(): String? {
        return try {
            encryptedPrefs.getString(KEY_REFRESH_TOKEN, null)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get refresh token")
            null
        }
    }

    fun getTokenExpiresAt(): Long {
        return try {
            encryptedPrefs.getLong(KEY_TOKEN_EXPIRES_AT, 0L)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get token expiry")
            0L
        }
    }

    fun isTokenExpired(): Boolean {
        val expiresAt = getTokenExpiresAt()
        return expiresAt <= System.currentTimeMillis()
    }

    fun getCurrentUserId(): String? {
        return try {
            encryptedPrefs.getString(KEY_USER_ID, null)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get user ID")
            null
        }
    }

    fun isLoggedIn(): Boolean {
        return try {
            encryptedPrefs.getBoolean(KEY_IS_LOGGED_IN, false) && 
            getAccessToken() != null &&
            !isTokenExpired()
        } catch (e: Exception) {
            Timber.e(e, "Failed to check login status")
            false
        }
    }
    
    fun getCurrentAuthState(): AuthState {
        return _authState.value
    }

    fun updateAccessToken(accessToken: String, expiresAt: Long) {
        try {
            encryptedPrefs.edit()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .putLong(KEY_TOKEN_EXPIRES_AT, expiresAt)
                .apply()
            
            Timber.d("Access token updated")
        } catch (e: Exception) {
            Timber.e(e, "Failed to update access token")
        }
    }

    fun clearAuthData() {
        try {
            encryptedPrefs.edit()
                .remove(KEY_ACCESS_TOKEN)
                .remove(KEY_REFRESH_TOKEN)
                .remove(KEY_TOKEN_EXPIRES_AT)
                .remove(KEY_USER_ID)
                .remove(KEY_USER_EMAIL)
                .remove(KEY_USER_NAME)
                .remove(KEY_USER_ROLE)
                .putBoolean(KEY_IS_LOGGED_IN, false)
                .apply()

            _authState.value = AuthState.Unauthenticated
            
            Timber.d("Auth data cleared")
        } catch (e: Exception) {
            Timber.e(e, "Failed to clear auth data")
        }
    }

    private fun checkAuthState() {
        try {
            if (isLoggedIn()) {
                val userId = encryptedPrefs.getString(KEY_USER_ID, null)
                val userEmail = encryptedPrefs.getString(KEY_USER_EMAIL, null)
                val userName = encryptedPrefs.getString(KEY_USER_NAME, null)
                val userRole = encryptedPrefs.getString(KEY_USER_ROLE, null)

                if (userId != null && userEmail != null && userName != null && userRole != null) {
                    _authState.value = AuthState.Authenticated(
                        userId = userId,
                        userEmail = userEmail,
                        userName = userName,
                        userRole = userRole
                    )
                } else {
                    _authState.value = AuthState.Unauthenticated
                }
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to check auth state")
            _authState.value = AuthState.Unauthenticated
        }
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    data class Authenticated(
        val userId: String,
        val userEmail: String,
        val userName: String,
        val userRole: String
    ) : AuthState()
}