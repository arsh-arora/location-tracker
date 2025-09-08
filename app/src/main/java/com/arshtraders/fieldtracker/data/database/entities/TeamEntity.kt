package com.arshtraders.fieldtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val managerId: String, // User ID of the team manager
    val createdBy: String, // User ID who created the team
    val isActive: Boolean = true,
    val createdAt: Long,
    val updatedAt: Long
)

@Entity(
    tableName = "team_members",
    primaryKeys = ["teamId", "userId"]
)
data class TeamMemberEntity(
    val teamId: String,
    val userId: String,
    val role: String = "MEMBER", // MEMBER, LEAD, MANAGER
    val joinedAt: Long,
    val isActive: Boolean = true
)

@Entity(tableName = "audit_logs")
data class AuditLogEntity(
    @PrimaryKey
    val id: String,
    val userId: String, // Who performed the action
    val action: String, // LOGIN, PUNCH_IN, PUNCH_OUT, USER_CREATED, etc.
    val entityType: String, // USER, PUNCH, PLACE, TEAM
    val entityId: String? = null, // ID of the affected entity
    val details: String? = null, // JSON string with additional details
    val timestamp: Long,
    val deviceInfo: String? = null,
    val ipAddress: String? = null
)