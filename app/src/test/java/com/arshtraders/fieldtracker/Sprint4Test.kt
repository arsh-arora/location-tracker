package com.arshtraders.fieldtracker

import com.arshtraders.fieldtracker.data.network.dto.*
import com.arshtraders.fieldtracker.sync.SyncWorker
import org.junit.Test
import org.junit.Assert.*

/**
 * Sprint 4: Offline Capabilities & Sync Tests
 * Tests sync functionality, network DTOs, and WorkManager integration
 */
class Sprint4Test {
    
    @Test
    fun `sync worker constants are properly defined`() {
        // Given
        val expectedWorkName = "sync_worker"
        val expectedBatchSize = 500
        
        // Then
        assertEquals(expectedWorkName, SyncWorker.WORK_NAME)
        assertEquals(expectedBatchSize, SyncWorker.BATCH_SIZE)
    }
    
    @Test
    fun `location batch request is properly structured`() {
        // Given
        val userId = "user_123"
        val deviceId = "device_456"
        val locationPoint = LocationBatchRequest.LocationPoint(
            timestamp = System.currentTimeMillis(),
            latitude = 22.7196,
            longitude = 75.8577,
            accuracy = 15.5f,
            speed = 5.2f,
            provider = "GPS",
            isMockLocation = false
        )
        
        // When
        val request = LocationBatchRequest(
            userId = userId,
            deviceId = deviceId,
            points = listOf(locationPoint)
        )
        
        // Then
        assertEquals(userId, request.userId)
        assertEquals(deviceId, request.deviceId)
        assertEquals(1, request.points.size)
        
        val point = request.points.first()
        assertEquals(22.7196, point.latitude, 0.0001)
        assertEquals(75.8577, point.longitude, 0.0001)
        assertEquals(15.5f, point.accuracy, 0.1f)
        assertEquals(5.2f, point.speed, 0.1f)
        assertEquals("GPS", point.provider)
        assertEquals(false, point.isMockLocation)
    }
    
    @Test
    fun `location batch response contains all fields`() {
        // Given
        val success = true
        val processed = 250
        val rejected = 5
        
        // When
        val response = LocationBatchResponse(
            success = success,
            processed = processed,
            rejected = rejected
        )
        
        // Then
        assertEquals(success, response.success)
        assertEquals(processed, response.processed)
        assertEquals(rejected, response.rejected)
    }
    
    @Test
    fun `punch upload request is properly structured`() {
        // Given
        val userId = "user_123"
        val placeId = "place_456"
        val type = "MANUAL_IN"
        val timestamp = System.currentTimeMillis()
        val latitude = 22.7196
        val longitude = 75.8577
        val accuracy = 12.3f
        
        // When
        val request = PunchUploadRequest(
            userId = userId,
            placeId = placeId,
            type = type,
            timestamp = timestamp,
            latitude = latitude,
            longitude = longitude,
            accuracy = accuracy
        )
        
        // Then
        assertEquals(userId, request.userId)
        assertEquals(placeId, request.placeId)
        assertEquals(type, request.type)
        assertEquals(timestamp, request.timestamp)
        assertEquals(latitude, request.latitude, 0.0001)
        assertEquals(longitude, request.longitude, 0.0001)
        assertEquals(accuracy, request.accuracy, 0.1f)
    }
    
    @Test
    fun `punch upload response contains all fields`() {
        // Given
        val punchId = "punch_789"
        val placeId = "place_456"
        val serverDistance = 45.2f
        
        // When
        val response = PunchUploadResponse(
            punchId = punchId,
            placeId = placeId,
            serverDistance = serverDistance
        )
        
        // Then
        assertEquals(punchId, response.punchId)
        assertEquals(placeId, response.placeId)
        assertEquals(serverDistance, response.serverDistance, 0.1f)
    }
    
    @Test
    fun `place dto contains all required fields`() {
        // Given
        val id = "place_123"
        val name = "Test Office"
        val latitude = 22.7196
        val longitude = 75.8577
        val radius = 150
        val status = "ACTIVE"
        
        // When
        val placeDto = PlaceDto(
            id = id,
            name = name,
            latitude = latitude,
            longitude = longitude,
            radius = radius,
            status = status
        )
        
        // Then
        assertEquals(id, placeDto.id)
        assertEquals(name, placeDto.name)
        assertEquals(latitude, placeDto.latitude, 0.0001)
        assertEquals(longitude, placeDto.longitude, 0.0001)
        assertEquals(radius, placeDto.radius)
        assertEquals(status, placeDto.status)
    }
    
    @Test
    fun `sync batch size meets performance requirements`() {
        // Given - Sprint 4 requirements for efficient sync
        val batchSize = SyncWorker.BATCH_SIZE
        
        // Then - Ensure batch size is optimized for network efficiency
        assertTrue("Batch size should be at least 100 for efficiency", batchSize >= 100)
        assertTrue("Batch size should not exceed 1000 to avoid timeouts", batchSize <= 1000)
    }
    
    @Test
    fun `location point nullable fields handle optional data`() {
        // Given
        val locationPoint = LocationBatchRequest.LocationPoint(
            timestamp = System.currentTimeMillis(),
            latitude = 22.7196,
            longitude = 75.8577,
            accuracy = 15.5f,
            speed = null, // Test nullable field
            provider = "NETWORK",
            isMockLocation = false
        )
        
        // Then
        assertNull(locationPoint.speed)
        assertNotNull(locationPoint.latitude)
        assertNotNull(locationPoint.longitude)
        assertEquals("NETWORK", locationPoint.provider)
    }
    
    @Test
    fun `punch upload request handles null place id`() {
        // Given
        val request = PunchUploadRequest(
            userId = "user_123",
            placeId = null, // Test nullable field
            type = "MANUAL_IN",
            timestamp = System.currentTimeMillis(),
            latitude = 22.7196,
            longitude = 75.8577,
            accuracy = 20.0f
        )
        
        // Then
        assertNull(request.placeId)
        assertNotNull(request.userId)
        assertNotNull(request.type)
    }
}