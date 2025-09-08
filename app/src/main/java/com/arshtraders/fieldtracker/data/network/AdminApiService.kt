package com.arshtraders.fieldtracker.data.network

import com.arshtraders.fieldtracker.data.network.dto.*
import com.arshtraders.fieldtracker.data.database.dao.AuditLogWithUser
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*

interface AdminApiService {
    
    // Dashboard & Analytics
    @GET("admin/dashboard/stats")
    suspend fun getDashboardStats(): Response<ApiResponse<DashboardStatsDto>>
    
    @GET("admin/users/locations")
    suspend fun getUserLocations(): Response<ApiResponse<List<UserLocationDto>>>
    
    @GET("admin/reports/attendance")
    suspend fun getAttendanceReport(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("user_id") userId: String? = null,
        @Query("team_id") teamId: String? = null
    ): Response<ApiResponse<List<AttendanceReportDto>>>
    
    // User Management
    @GET("admin/users")
    suspend fun getAllUsers(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50,
        @Query("search") search: String? = null,
        @Query("role") role: String? = null,
        @Query("status") status: String? = null
    ): Response<ApiResponse<List<UserManagementDto>>>
    
    @POST("admin/users")
    suspend fun createUser(@Body request: CreateUserRequestDto): Response<ApiResponse<UserManagementDto>>

    @GET("admin/users/{userId}")
    suspend fun getUserDetails(@Path("userId") userId: String): Response<ApiResponse<UserManagementDto>>
    
    @PUT("admin/users/{userId}")
    suspend fun updateUser(
        @Path("userId") userId: String,
        @Body request: UpdateUserRequestDto
    ): Response<ApiResponse<UserManagementDto>>

    @DELETE("admin/users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String): Response<ApiResponse<Unit>>

    @PUT("admin/users/{userId}/status")
    suspend fun updateUserStatus(
        @Path("userId") userId: String,
        @Query("is_active") isActive: Boolean
    ): Response<ApiResponse<UserManagementDto>>
    
    @POST("admin/users/bulk-action")
    suspend fun performBulkUserAction(@Body request: BulkUserActionRequestDto): Response<ApiResponse<BulkActionResponseDto>>
    
    // Team Management
    @GET("admin/teams")
    suspend fun getAllTeams(): Response<ApiResponse<List<TeamDto>>>
    
    @POST("admin/teams")
    suspend fun createTeam(@Body request: CreateTeamRequestDto): Response<ApiResponse<TeamDto>>
    
    @GET("admin/teams/{teamId}")
    suspend fun getTeamDetails(@Path("teamId") teamId: String): Response<ApiResponse<TeamDto>>
    
    @PUT("admin/teams/{teamId}")
    suspend fun updateTeam(
        @Path("teamId") teamId: String,
        @Body request: UpdateTeamRequestDto
    ): Response<ApiResponse<TeamDto>>
    
    @DELETE("admin/teams/{teamId}")
    suspend fun deleteTeam(@Path("teamId") teamId: String): Response<ApiResponse<Unit>>
    
    @GET("admin/teams/{teamId}/members")
    suspend fun getTeamMembers(@Path("teamId") teamId: String): Response<ApiResponse<List<TeamMemberDto>>>
    
    @POST("admin/teams/{teamId}/members")
    suspend fun addTeamMember(
        @Path("teamId") teamId: String,
        @Body request: Map<String, String> // {"user_id": "...", "role": "..."}
    ): Response<ApiResponse<TeamMemberDto>>
    
    @DELETE("admin/teams/{teamId}/members/{userId}")
    suspend fun removeTeamMember(
        @Path("teamId") teamId: String,
        @Path("userId") userId: String
    ): Response<ApiResponse<Unit>>
    
    @PUT("admin/teams/{teamId}/members/{userId}")
    suspend fun updateMemberRole(
        @Path("teamId") teamId: String,
        @Path("userId") userId: String,
        @Body request: Map<String, String> // {"role": "..."}
    ): Response<ApiResponse<TeamMemberDto>>
    
    // Push Notifications
    @POST("admin/notifications/send")
    suspend fun sendNotification(@Body request: SendNotificationRequestDto): Response<ApiResponse<Unit>>
    
    // Audit Logs
    @GET("admin/audit-logs")
    suspend fun getAuditLogs(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 50,
        @Query("user_id") userId: String? = null,
        @Query("action") action: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null
    ): Response<ApiResponse<List<AuditLogWithUser>>>
    
    // Places & Geofence Management
    @GET("admin/places")
    suspend fun getAllPlaces(): Response<ApiResponse<List<PlaceDto>>>
    
    @POST("admin/places")
    suspend fun createPlace(@Body request: CreatePlaceRequestDto): Response<ApiResponse<PlaceDto>>
    
    @PUT("admin/places/{placeId}")
    suspend fun updatePlace(
        @Path("placeId") placeId: String,
        @Body request: UpdatePlaceRequestDto
    ): Response<ApiResponse<PlaceDto>>
    
    @DELETE("admin/places/{placeId}")
    suspend fun deletePlace(@Path("placeId") placeId: String): Response<ApiResponse<Unit>>
}

// Additional DTOs for Places
data class PlaceDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("formatted_address")
    val formattedAddress: String?,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("radius")
    val radius: Int,
    @SerializedName("place_type")
    val placeType: String?,
    @SerializedName("status")
    val status: String,
    @SerializedName("created_by")
    val createdBy: String,
    @SerializedName("created_at")
    val createdAt: Long
)

data class CreatePlaceRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("radius")
    val radius: Int,
    @SerializedName("place_type")
    val placeType: String? = null
)

data class UpdatePlaceRequestDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("radius")
    val radius: Int?,
    @SerializedName("status")
    val status: String?
)