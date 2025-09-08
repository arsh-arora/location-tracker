package com.arshtraders.fieldtracker

import com.arshtraders.fieldtracker.data.database.entities.LocationPointEntity
import com.arshtraders.fieldtracker.data.database.entities.PlaceEntity
import com.arshtraders.fieldtracker.data.database.entities.PunchEntity
import org.junit.Test
import org.junit.Assert.*
import java.util.UUID

/**
 * Sprint 1 Foundation Tests
 * Tests basic entity creation and validation
 */
class Sprint1Test {
    
    @Test
    fun `location entity creation should work correctly`() {
        // Given
        val userId = "test_user_123"
        val deviceId = "test_device_456"
        val timestamp = System.currentTimeMillis()
        val latitude = 22.7196
        val longitude = 75.8577
        val accuracy = 15.5f
        val provider = "GPS"
        
        // When
        val locationEntity = LocationPointEntity(
            userId = userId,
            deviceId = deviceId,
            timestamp = timestamp,
            latitude = latitude,
            longitude = longitude,
            accuracy = accuracy,
            provider = provider
        )
        
        // Then
        assertNotNull(locationEntity.id)
        assertEquals(userId, locationEntity.userId)
        assertEquals(deviceId, locationEntity.deviceId)
        assertEquals(timestamp, locationEntity.timestamp)
        assertEquals(latitude, locationEntity.latitude, 0.0001)
        assertEquals(longitude, locationEntity.longitude, 0.0001)
        assertEquals(accuracy, locationEntity.accuracy, 0.001f)
        assertEquals(provider, locationEntity.provider)
        assertEquals(false, locationEntity.isMockLocation)
        assertEquals(false, locationEntity.isUploaded)
        assertTrue(locationEntity.createdAt > 0)
    }
    
    @Test
    fun `punch entity creation should work correctly`() {
        // Given
        val userId = "test_user_123"
        val placeId = "test_place_456"
        val type = "MANUAL_IN"
        val timestamp = System.currentTimeMillis()
        val latitude = 22.7196
        val longitude = 75.8577
        val accuracy = 12.3f
        
        // When
        val punchEntity = PunchEntity(
            userId = userId,
            placeId = placeId,
            type = type,
            timestamp = timestamp,
            latitude = latitude,
            longitude = longitude,
            accuracy = accuracy
        )
        
        // Then
        assertNotNull(punchEntity.id)
        assertEquals(userId, punchEntity.userId)
        assertEquals(placeId, punchEntity.placeId)
        assertEquals(type, punchEntity.type)
        assertEquals(timestamp, punchEntity.timestamp)
        assertEquals(latitude, punchEntity.latitude, 0.0001)
        assertEquals(longitude, punchEntity.longitude, 0.0001)
        assertEquals(accuracy, punchEntity.accuracy, 0.001f)
        assertEquals(false, punchEntity.isUploaded)
        assertEquals("PENDING", punchEntity.syncStatus)
        assertTrue(punchEntity.createdAt > 0)
    }
    
    @Test
    fun `place entity creation should work correctly`() {
        // Given
        val id = UUID.randomUUID().toString()
        val name = "Test Office"
        val latitude = 22.7196
        val longitude = 75.8577
        val radius = 150
        val status = "ACTIVE"
        val createdAt = System.currentTimeMillis()
        val updatedAt = System.currentTimeMillis()
        
        // When
        val placeEntity = PlaceEntity(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude,
            radius = radius,
            status = status,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
        
        // Then
        assertEquals(id, placeEntity.id)
        assertEquals(name, placeEntity.name)
        assertEquals(latitude, placeEntity.latitude, 0.0001)
        assertEquals(longitude, placeEntity.longitude, 0.0001)
        assertEquals(radius, placeEntity.radius)
        assertEquals(status, placeEntity.status)
        assertEquals(createdAt, placeEntity.createdAt)
        assertEquals(updatedAt, placeEntity.updatedAt)
    }
    
    @Test
    fun `default values should be set correctly`() {
        // When
        val locationEntity = LocationPointEntity(
            userId = "test_user",
            deviceId = "test_device",
            timestamp = System.currentTimeMillis(),
            latitude = 0.0,
            longitude = 0.0,
            accuracy = 10.0f,
            provider = "GPS"
        )
        
        // Then
        assertEquals(false, locationEntity.isMockLocation)
        assertEquals(false, locationEntity.isUploaded)
        assertNull(locationEntity.batteryLevel)
        assertNull(locationEntity.speed)
        assertTrue(locationEntity.createdAt > 0)
    }
}