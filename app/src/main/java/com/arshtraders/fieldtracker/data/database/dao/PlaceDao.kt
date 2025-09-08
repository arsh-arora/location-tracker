package com.arshtraders.fieldtracker.data.database.dao

import androidx.room.*
import com.arshtraders.fieldtracker.data.database.entities.PlaceEntity

@Dao
interface PlaceDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)
    
    @Query("SELECT * FROM places WHERE status = 'ACTIVE'")
    suspend fun getActivePlaces(): List<PlaceEntity>
    
    @Query("SELECT * FROM places WHERE id = :placeId")
    suspend fun getPlaceById(placeId: String): PlaceEntity?
    
    @Update
    suspend fun updatePlace(place: PlaceEntity)
}