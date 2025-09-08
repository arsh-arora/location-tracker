package com.arshtraders.fieldtracker.domain.auth

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoleManager @Inject constructor(
    private val tokenManager: TokenManager
) {
    
    companion object {
        // User Roles
        const val ROLE_USER = "USER"
        const val ROLE_TEAM_LEAD = "TEAM_LEAD"
        const val ROLE_MANAGER = "MANAGER"
        const val ROLE_ADMIN = "ADMIN"
        const val ROLE_SUPER_ADMIN = "SUPER_ADMIN"
        
        // Team Roles
        const val TEAM_ROLE_MEMBER = "MEMBER"
        const val TEAM_ROLE_LEAD = "LEAD"
        const val TEAM_ROLE_MANAGER = "MANAGER"
        
        // Permissions
        const val PERM_VIEW_DASHBOARD = "view_dashboard"
        const val PERM_MANAGE_USERS = "manage_users"
        const val PERM_MANAGE_TEAMS = "manage_teams"
        const val PERM_VIEW_ANALYTICS = "view_analytics"
        const val PERM_MANAGE_PLACES = "manage_places"
        const val PERM_SEND_NOTIFICATIONS = "send_notifications"
        const val PERM_VIEW_AUDIT_LOGS = "view_audit_logs"
        const val PERM_EXPORT_DATA = "export_data"
        const val PERM_BULK_OPERATIONS = "bulk_operations"
        const val PERM_SYSTEM_CONFIG = "system_config"
    }
    
    fun getCurrentUserRole(): String? {
        return try {
            val currentState = tokenManager.getCurrentAuthState()
            when (currentState) {
                is AuthState.Authenticated -> currentState.userRole
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun getCurrentUserId(): String? {
        return try {
            val currentState = tokenManager.getCurrentAuthState()
            when (currentState) {
                is AuthState.Authenticated -> currentState.userId
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    fun isAdmin(): Boolean {
        val role = getCurrentUserRole()
        return role in listOf(ROLE_ADMIN, ROLE_SUPER_ADMIN)
    }
    
    fun isSuperAdmin(): Boolean {
        return getCurrentUserRole() == ROLE_SUPER_ADMIN
    }
    
    fun isManagerOrAbove(): Boolean {
        val role = getCurrentUserRole()
        return role in listOf(ROLE_MANAGER, ROLE_ADMIN, ROLE_SUPER_ADMIN)
    }
    
    fun isTeamLeadOrAbove(): Boolean {
        val role = getCurrentUserRole()
        return role in listOf(ROLE_TEAM_LEAD, ROLE_MANAGER, ROLE_ADMIN, ROLE_SUPER_ADMIN)
    }
    
    fun hasPermission(permission: String): Boolean {
        val role = getCurrentUserRole() ?: return false
        
        return when (permission) {
            PERM_VIEW_DASHBOARD -> isTeamLeadOrAbove()
            
            PERM_MANAGE_USERS -> isManagerOrAbove()
            PERM_MANAGE_TEAMS -> isManagerOrAbove()
            PERM_BULK_OPERATIONS -> isManagerOrAbove()
            
            PERM_VIEW_ANALYTICS -> isTeamLeadOrAbove()
            PERM_EXPORT_DATA -> isManagerOrAbove()
            
            PERM_MANAGE_PLACES -> isAdmin()
            PERM_SEND_NOTIFICATIONS -> isManagerOrAbove()
            PERM_VIEW_AUDIT_LOGS -> isManagerOrAbove()
            PERM_SYSTEM_CONFIG -> isSuperAdmin()
            
            else -> false
        }
    }
    
    fun canViewUserData(targetUserId: String): Boolean {
        val currentUserId = getCurrentUserId()
        
        // Users can always view their own data
        if (currentUserId == targetUserId) {
            return true
        }
        
        // Managers and above can view all user data
        return isManagerOrAbove()
    }
    
    fun canManageUser(targetUserId: String): Boolean {
        val currentUserId = getCurrentUserId()
        
        // Users cannot manage themselves through admin panel
        if (currentUserId == targetUserId) {
            return false
        }
        
        // Only managers and above can manage users
        return isManagerOrAbove()
    }
    
    fun canManageTeam(teamManagerId: String? = null): Boolean {
        if (!isManagerOrAbove()) {
            return false
        }
        
        // Super admins can manage any team
        if (isSuperAdmin()) {
            return true
        }
        
        // Admins can manage any team
        if (isAdmin()) {
            return true
        }
        
        // Managers can only manage teams where they are the manager
        val currentUserId = getCurrentUserId()
        return teamManagerId == null || teamManagerId == currentUserId
    }
    
    fun getAccessibleRoles(): List<String> {
        return when (getCurrentUserRole()) {
            ROLE_SUPER_ADMIN -> listOf(ROLE_USER, ROLE_TEAM_LEAD, ROLE_MANAGER, ROLE_ADMIN)
            ROLE_ADMIN -> listOf(ROLE_USER, ROLE_TEAM_LEAD, ROLE_MANAGER)
            ROLE_MANAGER -> listOf(ROLE_USER, ROLE_TEAM_LEAD)
            else -> emptyList()
        }
    }
    
    fun getRoleDisplayName(role: String): String {
        return when (role) {
            ROLE_USER -> "User"
            ROLE_TEAM_LEAD -> "Team Lead"
            ROLE_MANAGER -> "Manager"
            ROLE_ADMIN -> "Admin"
            ROLE_SUPER_ADMIN -> "Super Admin"
            else -> role
        }
    }
    
    fun getTeamRoleDisplayName(role: String): String {
        return when (role) {
            TEAM_ROLE_MEMBER -> "Member"
            TEAM_ROLE_LEAD -> "Lead"
            TEAM_ROLE_MANAGER -> "Manager"
            else -> role
        }
    }
    
    fun getRoleColor(role: String): String {
        return when (role) {
            ROLE_SUPER_ADMIN -> "#FF0000" // Red
            ROLE_ADMIN -> "#FF6600" // Orange
            ROLE_MANAGER -> "#0066FF" // Blue
            ROLE_TEAM_LEAD -> "#00AA00" // Green
            ROLE_USER -> "#666666" // Gray
            else -> "#666666"
        }
    }
}