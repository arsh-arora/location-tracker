package com.arshtraders.fieldtracker.data.database.dao

import androidx.room.*
import com.arshtraders.fieldtracker.data.database.entities.PunchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PunchDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPunch(punch: PunchEntity)
    
    @Query("SELECT * FROM punches WHERE isUploaded = 0 ORDER BY timestamp ASC")
    suspend fun getPendingPunches(): List<PunchEntity>
    
    @Query("UPDATE punches SET isUploaded = 1, syncStatus = 'SYNCED' WHERE id = :id")
    suspend fun markAsUploaded(id: String)
    
    @Query("SELECT * FROM punches WHERE DATE(timestamp/1000, 'unixepoch', 'localtime') = DATE('now', 'localtime') ORDER BY timestamp DESC")
    fun getTodaysPunches(): Flow<List<PunchEntity>>
    
    @Query("SELECT * FROM punches ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastPunch(): PunchEntity?
    
    @Query("""
        SELECT 
            p.id,
            p.userId,
            p.placeId,
            p.type,
            p.timestamp,
            p.latitude,
            p.longitude,
            p.accuracy,
            p.serverDistance,
            p.isUploaded,
            p.syncStatus,
            pl.name as placeName,
            pl.formattedAddress as placeAddress
        FROM punches p 
        LEFT JOIN places pl ON p.placeId = pl.id 
        ORDER BY p.timestamp DESC 
        LIMIT 1
    """)
    suspend fun getLastPunchWithPlace(): PunchWithPlace?
    
    @Query("DELETE FROM punches WHERE isUploaded = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldUploadedPunches(cutoffTime: Long)
}

/**
 * Data class representing a punch with its associated place information
 */
data class PunchWithPlace(
    val id: String,
    val userId: String,
    val placeId: String,
    val type: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val serverDistance: Float?,
    val isUploaded: Boolean,
    val syncStatus: String,
    val placeName: String?,
    val placeAddress: String?
)