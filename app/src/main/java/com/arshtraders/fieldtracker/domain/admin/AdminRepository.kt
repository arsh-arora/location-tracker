package com.arshtraders.fieldtracker.domain.admin

import com.arshtraders.fieldtracker.data.database.dao.UserDao
import com.arshtraders.fieldtracker.data.database.dao.TeamDao
import com.arshtraders.fieldtracker.data.database.dao.AuditLogDao
import com.arshtraders.fieldtracker.data.database.entities.AuditLogEntity
import com.arshtraders.fieldtracker.data.network.AdminApiService
import com.arshtraders.fieldtracker.data.network.dto.*
import com.arshtraders.fieldtracker.domain.auth.RoleManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdminRepository @Inject constructor(
    private val adminApiService: AdminApiService,
    private val userDao: UserDao,
    private val teamDao: TeamDao,
    private val auditLogDao: AuditLogDao,
    private val roleManager: RoleManager
) {
    
    suspend fun getDashboardStats(): AdminResult<DashboardStatsDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_VIEW_DASHBOARD)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.getDashboardStats()
            if (response.isSuccessful) {
                response.body()?.data?.let { stats ->
                    AdminResult.Success(stats)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to fetch dashboard stats: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching dashboard stats")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun getAllUsers(
        page: Int = 1,
        search: String? = null,
        role: String? = null,
        status: String? = null
    ): AdminResult<List<UserManagementDto>> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_USERS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.getAllUsers(
                page = page,
                search = search,
                role = role,
                status = status
            )
            
            if (response.isSuccessful) {
                response.body()?.data?.let { users ->
                    AdminResult.Success(users)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to fetch users: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching users")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    
    suspend fun getAllTeams(): AdminResult<List<TeamDto>> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_TEAMS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.getAllTeams()
            if (response.isSuccessful) {
                response.body()?.data?.let { teams ->
                    AdminResult.Success(teams)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to fetch teams: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching teams")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun createUser(request: CreateUserRequestDto): AdminResult<UserManagementDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_USERS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.createUser(request)
            if (response.isSuccessful) {
                response.body()?.data?.let { user ->
                    // Log the action
                    logAuditAction(
                        action = "USER_CREATED",
                        entityType = "USER",
                        entityId = user.id,
                        details = "User '${user.name}' created with role ${user.role}"
                    )
                    AdminResult.Success(user)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to create user: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating user")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun updateUser(userId: String, request: UpdateUserRequestDto): AdminResult<UserManagementDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.canManageUser(userId)) {
                return@withContext AdminResult.Error("Access denied: Cannot manage this user")
            }
            
            val response = adminApiService.updateUser(userId, request)
            if (response.isSuccessful) {
                response.body()?.data?.let { user ->
                    // Log the action
                    logAuditAction(
                        action = "USER_UPDATED",
                        entityType = "USER",
                        entityId = userId,
                        details = "User updated: ${request.name}"
                    )
                    AdminResult.Success(user)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to update user: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating user")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun deleteUser(userId: String): AdminResult<Unit> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.canManageUser(userId)) {
                return@withContext AdminResult.Error("Access denied: Cannot manage this user")
            }
            
            val response = adminApiService.deleteUser(userId)
            if (response.isSuccessful) {
                // Log the action
                logAuditAction(
                    action = "USER_DELETED",
                    entityType = "USER",
                    entityId = userId,
                    details = "User deleted"
                )
                AdminResult.Success(Unit)
            } else {
                AdminResult.Error("Failed to delete user: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting user")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun updateUserStatus(userId: String, isActive: Boolean): AdminResult<UserManagementDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.canManageUser(userId)) {
                return@withContext AdminResult.Error("Access denied: Cannot manage this user")
            }
            
            val response = adminApiService.updateUserStatus(userId, isActive)
            if (response.isSuccessful) {
                response.body()?.data?.let { user ->
                    // Log the action
                    logAuditAction(
                        action = "USER_STATUS_UPDATED",
                        entityType = "USER",
                        entityId = userId,
                        details = "User status changed to ${if (isActive) "active" else "inactive"}"
                    )
                    AdminResult.Success(user)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to update user status: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating user status")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun createTeam(request: CreateTeamRequestDto): AdminResult<TeamDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_TEAMS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.createTeam(request)
            if (response.isSuccessful) {
                response.body()?.data?.let { team ->
                    // Log the action
                    logAuditAction(
                        action = "TEAM_CREATED",
                        entityType = "TEAM",
                        entityId = team.id,
                        details = "Team '${request.name}' created with manager ${request.managerId}"
                    )
                    AdminResult.Success(team)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to create team: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating team")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun updateTeam(teamId: String, request: UpdateTeamRequestDto): AdminResult<TeamDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_TEAMS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.updateTeam(teamId, request)
            if (response.isSuccessful) {
                response.body()?.data?.let { team ->
                    // Log the action
                    logAuditAction(
                        action = "TEAM_UPDATED",
                        entityType = "TEAM",
                        entityId = teamId,
                        details = "Team updated: ${request.name}"
                    )
                    AdminResult.Success(team)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to update team: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error updating team")
            AdminResult.Error("Network error: ${e.message}")
        }
    }

    suspend fun deleteTeam(teamId: String): AdminResult<Unit> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_MANAGE_TEAMS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.deleteTeam(teamId)
            if (response.isSuccessful) {
                // Log the action
                logAuditAction(
                    action = "TEAM_DELETED",
                    entityType = "TEAM",
                    entityId = teamId,
                    details = "Team deleted"
                )
                AdminResult.Success(Unit)
            } else {
                AdminResult.Error("Failed to delete team: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting team")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun getUserLocations(): AdminResult<List<UserLocationDto>> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_VIEW_ANALYTICS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.getUserLocations()
            if (response.isSuccessful) {
                response.body()?.data?.let { locations ->
                    AdminResult.Success(locations)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to fetch user locations: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching user locations")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun getAttendanceReport(
        startDate: String,
        endDate: String,
        userId: String? = null,
        teamId: String? = null
    ): AdminResult<List<AttendanceReportDto>> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_VIEW_ANALYTICS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val response = adminApiService.getAttendanceReport(startDate, endDate, userId, teamId)
            if (response.isSuccessful) {
                response.body()?.data?.let { report ->
                    AdminResult.Success(report)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to fetch attendance report: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching attendance report")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun sendNotification(
        title: String,
        message: String,
        userIds: List<String>? = null,
        teamIds: List<String>? = null,
        type: String = "GENERAL"
    ): AdminResult<Unit> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_SEND_NOTIFICATIONS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val request = SendNotificationRequestDto(
                userIds = userIds,
                teamIds = teamIds,
                title = title,
                message = message,
                type = type
            )
            
            val response = adminApiService.sendNotification(request)
            if (response.isSuccessful) {
                // Log the action
                val recipientInfo = when {
                    userIds != null -> "users: ${userIds.size}"
                    teamIds != null -> "teams: ${teamIds.size}"
                    else -> "all users"
                }
                
                logAuditAction(
                    action = "NOTIFICATION_SENT",
                    entityType = "NOTIFICATION",
                    details = "Sent to $recipientInfo - Title: $title"
                )
                AdminResult.Success(Unit)
            } else {
                AdminResult.Error("Failed to send notification: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error sending notification")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    suspend fun performBulkUserAction(
        userIds: List<String>,
        action: String,
        data: Map<String, Any>? = null
    ): AdminResult<BulkActionResponseDto> = withContext(Dispatchers.IO) {
        try {
            // Check permissions
            if (!roleManager.hasPermission(RoleManager.PERM_BULK_OPERATIONS)) {
                return@withContext AdminResult.Error("Access denied: Insufficient permissions")
            }
            
            val request = BulkUserActionRequestDto(
                userIds = userIds,
                action = action,
                data = data
            )
            
            val response = adminApiService.performBulkUserAction(request)
            if (response.isSuccessful) {
                response.body()?.data?.let { result ->
                    // Log the action
                    logAuditAction(
                        action = "BULK_USER_ACTION",
                        entityType = "USER",
                        details = "Action: $action on ${userIds.size} users. Success: ${result.successCount}, Failed: ${result.failureCount}"
                    )
                    AdminResult.Success(result)
                } ?: AdminResult.Error("No data received")
            } else {
                AdminResult.Error("Failed to perform bulk action: ${response.code()}")
            }
        } catch (e: Exception) {
            Timber.e(e, "Error performing bulk action")
            AdminResult.Error("Network error: ${e.message}")
        }
    }
    
    private suspend fun logAuditAction(
        action: String,
        entityType: String,
        entityId: String? = null,
        details: String? = null
    ) {
        try {
            val userId = roleManager.getCurrentUserId() ?: return
            
            val auditLog = AuditLogEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                action = action,
                entityType = entityType,
                entityId = entityId,
                details = details,
                timestamp = System.currentTimeMillis()
            )
            
            auditLogDao.insertAuditLog(auditLog)
        } catch (e: Exception) {
            Timber.w(e, "Failed to log audit action")
        }
    }
}

sealed class AdminResult<T> {
    data class Success<T>(val data: T) : AdminResult<T>()
    data class Error<T>(val message: String) : AdminResult<T>()
}