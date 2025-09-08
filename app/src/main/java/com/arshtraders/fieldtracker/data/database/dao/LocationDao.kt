package com.arshtraders.fieldtracker.data.database.dao

import androidx.room.*
import com.arshtraders.fieldtracker.data.database.entities.LocationPointEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationPointEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationPointEntity>)
    
    @Query("SELECT * FROM location_points WHERE isUploaded = 0 ORDER BY timestamp ASC LIMIT :limit")
    suspend fun getPendingUploads(limit: Int = 500): List<LocationPointEntity>
    
    @Query("UPDATE location_points SET isUploaded = 1 WHERE id IN (:ids)")
    suspend fun markAsUploaded(ids: List<String>)
    
    @Query("SELECT * FROM location_points WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    fun getLocationsAfter(startTime: Long): Flow<List<LocationPointEntity>>
    
    @Query("DELETE FROM location_points WHERE isUploaded = 1 AND timestamp < :cutoffTime")
    suspend fun deleteOldUploadedLocations(cutoffTime: Long)
    
    @Query("SELECT COUNT(*) FROM location_points")
    suspend fun getTotalPointCount(): Int
    
    @Query("SELECT COUNT(*) FROM location_points WHERE isUploaded = 0")
    suspend fun getPendingPointCount(): Int
}