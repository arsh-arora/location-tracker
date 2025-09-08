package com.arshtraders.fieldtracker.data.database.dao

import androidx.room.*
import com.arshtraders.fieldtracker.data.database.entities.AuditLogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuditLogDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(auditLog: AuditLogEntity)
    
    @Query("""
        SELECT al.*, u.name as userName, u.email as userEmail 
        FROM audit_logs al 
        LEFT JOIN users u ON al.userId = u.id 
        ORDER BY al.timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getAuditLogs(limit: Int = 50, offset: Int = 0): List<AuditLogWithUser>
    
    @Query("""
        SELECT al.*, u.name as userName, u.email as userEmail 
        FROM audit_logs al 
        LEFT JOIN users u ON al.userId = u.id 
        WHERE al.userId = :userId 
        ORDER BY al.timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getUserAuditLogs(userId: String, limit: Int = 50, offset: Int = 0): List<AuditLogWithUser>
    
    @Query("""
        SELECT al.*, u.name as userName, u.email as userEmail 
        FROM audit_logs al 
        LEFT JOIN users u ON al.userId = u.id 
        WHERE al.action = :action 
        ORDER BY al.timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getAuditLogsByAction(action: String, limit: Int = 50, offset: Int = 0): List<AuditLogWithUser>
    
    @Query("""
        SELECT al.*, u.name as userName, u.email as userEmail 
        FROM audit_logs al 
        LEFT JOIN users u ON al.userId = u.id 
        WHERE al.timestamp BETWEEN :startTime AND :endTime 
        ORDER BY al.timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getAuditLogsByDateRange(
        startTime: Long, 
        endTime: Long, 
        limit: Int = 50, 
        offset: Int = 0
    ): List<AuditLogWithUser>
    
    @Query("""
        SELECT COUNT(*) 
        FROM audit_logs 
        WHERE timestamp BETWEEN :startTime AND :endTime
    """)
    suspend fun getAuditLogCount(startTime: Long, endTime: Long): Int
    
    @Query("DELETE FROM audit_logs WHERE timestamp < :cutoffTime")
    suspend fun deleteOldAuditLogs(cutoffTime: Long)
}

data class AuditLogWithUser(
    val id: String,
    val userId: String,
    val action: String,
    val entityType: String,
    val entityId: String?,
    val details: String?,
    val timestamp: Long,
    val deviceInfo: String?,
    val ipAddress: String?,
    val userName: String?,
    val userEmail: String?
)