package com.arshtraders.fieldtracker.data.supabase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Punch(
    val id: String? = null,
    @SerialName("user_id") val userId: String,
    val type: String,              // "PUNCH_IN" | "PUNCH_OUT"
    val latitude: Double? = null,
    val longitude: Double? = null,
    val accuracy: Double? = null,
    @SerialName("place_name") val placeName: String? = null,
    val address: String? = null,
    val timestamp: String? = null, // server default
    @SerialName("created_at") val createdAt: String? = null
)

@Serializable
data class Profile(
    val id: String,
    val email: String,
    val name: String,
    val role: String? = "USER",
    @SerialName("phone_number") val phoneNumber: String? = null,
    @SerialName("employee_id") val employeeId: String? = null,
    val department: String? = null,
    @SerialName("profile_picture_url") val profilePictureUrl: String? = null,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class LocationPoint(
    val id: String? = null,
    @SerialName("user_id") val userId: String,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Double? = null,
    val altitude: Double? = null,
    val speed: Double? = null,
    val bearing: Double? = null,
    val provider: String? = null,
    val timestamp: String? = null,
    @SerialName("created_at") val createdAt: String? = null
)

@Serializable
data class Place(
    val id: String? = null,
    @SerialName("user_id") val userId: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val radius: Double? = 100.0,
    val address: String? = null,
    @SerialName("place_type") val placeType: String? = null,
    val description: String? = null,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class Team(
    val id: String? = null,
    val name: String,
    val description: String? = null,
    @SerialName("created_by") val createdBy: String,
    @SerialName("is_active") val isActive: Boolean = true,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

@Serializable
data class TeamMember(
    val id: String? = null,
    @SerialName("team_id") val teamId: String,
    @SerialName("user_id") val userId: String,
    val role: String = "MEMBER", // "LEADER" | "MEMBER"
    @SerialName("joined_at") val joinedAt: String? = null
)