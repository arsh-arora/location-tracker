package com.arshtraders.fieldtracker.data.network

import com.arshtraders.fieldtracker.data.network.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequestDto): Response<AuthResponseDto>
    
    @POST("auth/register") 
    suspend fun register(@Body request: RegisterRequestDto): Response<AuthResponseDto>
    
    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequestDto): Response<RefreshTokenResponseDto>
    
    @POST("auth/logout")
    suspend fun logout(): Response<ApiResponse<Unit>>
    
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body request: Map<String, String>): Response<ApiResponse<Unit>>
}