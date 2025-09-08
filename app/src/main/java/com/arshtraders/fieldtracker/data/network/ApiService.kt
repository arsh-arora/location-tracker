package com.arshtraders.fieldtracker.data.network

import com.arshtraders.fieldtracker.data.network.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    @POST("locations/batch")
    suspend fun uploadLocations(
        @Body request: LocationBatchRequest
    ): Response<LocationBatchResponse>
    
    @POST("punches")
    suspend fun uploadPunch(
        @Body request: PunchUploadRequest
    ): Response<PunchUploadResponse>
    
    @GET("places/active")
    suspend fun getActivePlaces(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("radius") radiusKm: Int = 5
    ): Response<List<PlaceDto>>
}