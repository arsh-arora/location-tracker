package com.arshtraders.fieldtracker.data.network.dto

import com.google.gson.annotations.SerializedName

// Team Management DTOs
data class CreateTeamRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("manager_id")
    val managerId: String
)

data class TeamDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("manager_id")
    val managerId: String,
    @SerializedName("manager_name")
    val managerName: String?,
    @SerializedName("member_count")
    val memberCount: Int,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("updated_at")
    val updatedAt: Long
)

data class TeamMemberDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("user_email")
    val userEmail: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("joined_at")
    val joinedAt: Long,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("last_seen")
    val lastSeen: Long?
)

// User Management DTOs
data class UserManagementDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("role")
    val role: String,
    @SerializedName("department")
    val department: String?,
    @SerializedName("employee_id")
    val employeeId: String?,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("last_login_at")
    val lastLoginAt: Long?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("total_punches")
    val totalPunches: Int,
    @SerializedName("teams")
    val teams: List<String>
)

data class CreateUserRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("employee_id")
    val employeeId: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("role")
    val role: String
)

data class UpdateUserRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("employee_id")
    val employeeId: String?,
    @SerializedName("department")
    val department: String?,
    @SerializedName("role")
    val role: String
)

data class UpdateTeamRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("manager_id")
    val managerId: String
)

// Analytics DTOs
data class DashboardStatsDto(
    @SerializedName("total_users")
    val totalUsers: Int,
    @SerializedName("active_users_today")
    val activeUsersToday: Int,
    @SerializedName("total_punches_today")
    val totalPunchesToday: Int,
    @SerializedName("total_teams")
    val totalTeams: Int,
    @SerializedName("online_users")
    val onlineUsers: Int,
    @SerializedName("recent_activities")
    val recentActivities: List<ActivityDto>
)

data class ActivityDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("action")
    val action: String,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("location")
    val location: String?
)

data class UserLocationDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("accuracy")
    val accuracy: Float,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("place_name")
    val placeName: String?,
    @SerializedName("status")
    val status: String // PUNCHED_IN, PUNCHED_OUT, OFFLINE
)

data class AttendanceReportDto(
    @SerializedName("date")
    val date: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("punch_in_time")
    val punchInTime: Long?,
    @SerializedName("punch_out_time")
    val punchOutTime: Long?,
    @SerializedName("total_hours")
    val totalHours: Float,
    @SerializedName("places_visited")
    val placesVisited: Int,
    @SerializedName("status")
    val status: String // PRESENT, ABSENT, HALF_DAY
)

// Bulk Operations DTOs
data class BulkUserActionRequestDto(
    @SerializedName("user_ids")
    val userIds: List<String>,
    @SerializedName("action")
    val action: String, // ACTIVATE, DEACTIVATE, DELETE, ASSIGN_TEAM
    @SerializedName("data")
    val data: Map<String, Any>? = null
)

data class BulkActionResponseDto(
    @SerializedName("success_count")
    val successCount: Int,
    @SerializedName("failure_count")
    val failureCount: Int,
    @SerializedName("errors")
    val errors: List<BulkActionErrorDto>
)

data class BulkActionErrorDto(
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("error")
    val error: String
)

// Push Notification DTOs
data class SendNotificationRequestDto(
    @SerializedName("user_ids")
    val userIds: List<String>? = null, // null means all users
    @SerializedName("team_ids")
    val teamIds: List<String>? = null,
    @SerializedName("title")
    val title: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("type")
    val type: String = "GENERAL", // GENERAL, URGENT, REMINDER
    @SerializedName("data")
    val data: Map<String, String>? = null
)