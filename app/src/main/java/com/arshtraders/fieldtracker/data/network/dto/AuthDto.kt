package com.arshtraders.fieldtracker.data.network.dto

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("device_info")
    val deviceInfo: String
)

data class RegisterRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("employee_id")
    val employeeId: String? = null,
    @SerializedName("department")
    val department: String? = null,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("device_info")
    val deviceInfo: String
)

data class AuthResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: AuthDataDto?
)

data class AuthDataDto(
    @SerializedName("user")
    val user: UserDto,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("expires_at")
    val expiresAt: Long
)

data class UserDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("profile_picture_url")
    val profilePictureUrl: String?,
    @SerializedName("phone_number")
    val phoneNumber: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("employee_id")
    val employeeId: String?,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long
)

data class RefreshTokenRequestDto(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("device_id")
    val deviceId: String
)

data class RefreshTokenResponseDto(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: RefreshTokenDataDto?
)

data class RefreshTokenDataDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_at")
    val expiresAt: Long
)