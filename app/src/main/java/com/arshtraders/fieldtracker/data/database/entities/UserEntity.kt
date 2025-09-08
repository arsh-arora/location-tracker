package com.arshtraders.fieldtracker.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val role: String = "USER", // USER, ADMIN, MANAGER
    val profilePictureUrl: String? = null,
    val phoneNumber: String? = null,
    val department: String? = null,
    val employeeId: String? = null,
    val isActive: Boolean = true,
    val createdAt: Long,
    val updatedAt: Long,
    val lastLoginAt: Long? = null
)