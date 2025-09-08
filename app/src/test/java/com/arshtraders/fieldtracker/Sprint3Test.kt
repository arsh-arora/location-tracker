package com.arshtraders.fieldtracker

import com.arshtraders.fieldtracker.domain.punch.PunchManager
import com.arshtraders.fieldtracker.domain.punch.PunchResult
import com.arshtraders.fieldtracker.domain.punch.PunchType
import com.arshtraders.fieldtracker.presentation.punch.PunchInfo
import com.arshtraders.fieldtracker.presentation.punch.PunchUiState
import org.junit.Test
import org.junit.Assert.*

/**
 * Sprint 3: Punch System Implementation Tests
 * Tests punch functionality, place detection, and UI state management
 */
class Sprint3Test {
    
    @Test
    fun `punch manager constants are properly defined`() {
        // Given
        val expectedDetectionRadius = 220.0 // meters
        val expectedAccuracyThreshold = 150f // meters
        
        // Then
        assertEquals(expectedDetectionRadius, PunchManager.PLACE_DETECTION_RADIUS, 0.1)
        assertEquals(expectedAccuracyThreshold, PunchManager.MIN_ACCURACY_THRESHOLD, 0.1f)
    }
    
    @Test
    fun `punch types are properly defined`() {
        // Given
        val expectedTypes = arrayOf("MANUAL_IN", "MANUAL_OUT", "AUTO_IN", "AUTO_OUT")
        
        // Then
        assertEquals(expectedTypes[0], PunchType.MANUAL_IN.name)
        assertEquals(expectedTypes[1], PunchType.MANUAL_OUT.name)
        assertEquals(expectedTypes[2], PunchType.AUTO_IN.name)
        assertEquals(expectedTypes[3], PunchType.AUTO_OUT.name)
    }
    
    @Test
    fun `punch result success contains all required fields`() {
        // Given
        val punchId = "punch_123"
        val placeId = "place_456"
        val placeName = "Test Office"
        val serverDistance = 25.5f
        
        // When
        val result = PunchResult.Success(
            punchId = punchId,
            placeId = placeId,
            placeName = placeName,
            serverDistance = serverDistance
        )
        
        // Then
        assertEquals(punchId, result.punchId)
        assertEquals(placeId, result.placeId)
        assertEquals(placeName, result.placeName)
        assertEquals(serverDistance, result.serverDistance, 0.1f)
    }
    
    @Test
    fun `punch result error contains message`() {
        // Given
        val errorMessage = "Location accuracy too low: 200.0m"
        
        // When
        val result = PunchResult.Error(errorMessage)
        
        // Then
        assertEquals(errorMessage, result.message)
    }
    
    @Test
    fun `punch ui state idle has correct default values`() {
        // When
        val uiState = PunchUiState.Idle()
        
        // Then
        assertNull(uiState.lastPunch)
    }
    
    @Test
    fun `punch ui state idle with last punch`() {
        // Given
        val punchInfo = PunchInfo(
            id = "punch_123",
            placeName = "Test Place",
            type = "MANUAL_IN",
            timestamp = System.currentTimeMillis()
        )
        
        // When
        val uiState = PunchUiState.Idle(punchInfo)
        
        // Then
        assertEquals(punchInfo, uiState.lastPunch)
    }
    
    @Test
    fun `punch ui state success contains all fields`() {
        // Given
        val punchId = "punch_123"
        val placeId = "place_456" 
        val placeName = "Test Office"
        val serverDistance = 45.2f
        
        // When
        val uiState = PunchUiState.Success(
            punchId = punchId,
            placeId = placeId,
            placeName = placeName,
            serverDistance = serverDistance
        )
        
        // Then
        assertEquals(punchId, uiState.punchId)
        assertEquals(placeId, uiState.placeId)
        assertEquals(placeName, uiState.placeName)
        assertEquals(serverDistance, uiState.serverDistance, 0.1f)
    }
    
    @Test
    fun `punch ui state error contains message`() {
        // Given
        val errorMessage = "Failed to get location"
        
        // When
        val uiState = PunchUiState.Error(errorMessage)
        
        // Then
        assertEquals(errorMessage, uiState.message)
    }
    
    @Test
    fun `punch info contains all required fields`() {
        // Given
        val id = "punch_123"
        val placeName = "Test Office"
        val type = "MANUAL_IN"
        val timestamp = System.currentTimeMillis()
        
        // When
        val punchInfo = PunchInfo(
            id = id,
            placeName = placeName,
            type = type,
            timestamp = timestamp
        )
        
        // Then
        assertEquals(id, punchInfo.id)
        assertEquals(placeName, punchInfo.placeName)
        assertEquals(type, punchInfo.type)
        assertEquals(timestamp, punchInfo.timestamp)
    }
    
    @Test
    fun `place detection radius meets requirements`() {
        // Given - Sprint 3 requirements for place detection
        val detectionRadius = PunchManager.PLACE_DETECTION_RADIUS
        
        // Then - Ensure radius is appropriate for field work
        assertTrue("Detection radius should be at least 200m for field environments", 
            detectionRadius >= 200.0)
        assertTrue("Detection radius should not exceed 300m to avoid false matches", 
            detectionRadius <= 300.0)
    }
    
    @Test
    fun `accuracy threshold meets requirements`() {
        // Given - Sprint 3 requirements for location accuracy
        val accuracyThreshold = PunchManager.MIN_ACCURACY_THRESHOLD
        
        // Then - Ensure accuracy threshold is reasonable for punch validation
        assertTrue("Accuracy threshold should be at least 100m for usable locations", 
            accuracyThreshold >= 100f)
        assertTrue("Accuracy threshold should not exceed 200m for punch precision", 
            accuracyThreshold <= 200f)
    }
    
    @Test
    fun `punch ui loading state exists`() {
        // When
        val uiState = PunchUiState.Loading
        
        // Then
        assertNotNull(uiState)
        assertTrue("Loading state should be object type", uiState is PunchUiState.Loading)
    }
}