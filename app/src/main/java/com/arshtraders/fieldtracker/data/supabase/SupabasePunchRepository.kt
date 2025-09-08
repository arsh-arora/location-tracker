package com.arshtraders.fieldtracker.data.supabase

import com.arshtraders.fieldtracker.data.database.entities.PunchEntity
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SupabasePunchRepository @Inject constructor(
    private val supabaseClient: SupabaseClient,
    private val authRepository: SupabaseAuthRepository
) {
    private val punches = supabaseClient.from("punches")
    private var realtimeChannel: RealtimeChannel? = null
    
    private val _recentPunches = MutableStateFlow<List<Punch>>(emptyList())
    val recentPunches: Flow<List<Punch>> = _recentPunches.asStateFlow()

    suspend fun punchIn(
        latitude: Double?, 
        longitude: Double?, 
        accuracy: Double?,
        placeName: String? = null,
        address: String? = null
    ): Result<Punch> = withContext(Dispatchers.IO) {
        try {
            val userId = authRepository.getCurrentUserId()
                ?: return@withContext Result.failure(Exception("No authenticated user"))

            val payload = Punch(
                userId = userId,
                type = "PUNCH_IN",
                latitude = latitude,
                longitude = longitude,
                accuracy = accuracy,
                placeName = placeName,
                address = address
            )

            val insertedPunch = punches.insert(payload) {
                select()
            }.decodeSingle<Punch>()

            Timber.d("Punch in successful: ${insertedPunch.id}")
            Result.success(insertedPunch)
        } catch (e: Exception) {
            Timber.e(e, "Punch in error")
            Result.failure(e)
        }
    }

    suspend fun punchOut(
        latitude: Double?, 
        longitude: Double?, 
        accuracy: Double?,
        placeName: String? = null,
        address: String? = null
    ): Result<Punch> = withContext(Dispatchers.IO) {
        try {
            val userId = authRepository.getCurrentUserId()
                ?: return@withContext Result.failure(Exception("No authenticated user"))

            val payload = Punch(
                userId = userId,
                type = "PUNCH_OUT",
                latitude = latitude,
                longitude = longitude,
                accuracy = accuracy,
                placeName = placeName,
                address = address
            )

            val insertedPunch = punches.insert(payload) {
                select()
            }.decodeSingle<Punch>()

            Timber.d("Punch out successful: ${insertedPunch.id}")
            Result.success(insertedPunch)
        } catch (e: Exception) {
            Timber.e(e, "Punch out error")
            Result.failure(e)
        }
    }

    suspend fun getMyRecentPunches(limit: Int = 20): List<Punch> = withContext(Dispatchers.IO) {
        try {
            val userId = authRepository.getCurrentUserId()
                ?: return@withContext emptyList()

            val result = punches.select {
                filter { 
                    eq("user_id", userId) 
                }
                order("timestamp", order = Order.DESCENDING)
                limit(limit.toLong())
            }.decodeList<Punch>()

            _recentPunches.value = result
            result
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch recent punches")
            emptyList()
        }
    }

    suspend fun getLastPunch(): Punch? = withContext(Dispatchers.IO) {
        try {
            val userId = authRepository.getCurrentUserId()
                ?: return@withContext null

            val result = punches.select {
                filter { 
                    eq("user_id", userId) 
                }
                order("timestamp", order = Order.DESCENDING)
                limit(1)
            }.decodeList<Punch>()

            result.firstOrNull()
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch last punch")
            null
        }
    }

    suspend fun getAllPunches(): List<PunchEntity> = withContext(Dispatchers.IO) {
        try {
            val userId = authRepository.getCurrentUserId()
                ?: return@withContext emptyList()

            val supabasePunches = punches.select {
                filter { 
                    eq("user_id", userId) 
                }
                order("timestamp", order = Order.DESCENDING)
            }.decodeList<Punch>()

            // Convert to PunchEntity for compatibility with existing code
            supabasePunches.map { punch ->
                PunchEntity(
                    id = punch.id ?: "",
                    userId = punch.userId,
                    type = punch.type,
                    timestamp = punch.timestamp?.toLongOrNull() ?: System.currentTimeMillis(),
                    latitude = punch.latitude ?: 0.0,
                    longitude = punch.longitude ?: 0.0,
                    accuracy = punch.accuracy?.toFloat() ?: 0.0f,
                    isUploaded = true, // Since it's from Supabase, it's already uploaded
                    syncStatus = "SYNCED",
                    createdAt = punch.createdAt?.toLongOrNull() ?: System.currentTimeMillis()
                )
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch all punches")
            emptyList()
        }
    }

    suspend fun startRealtimeUpdates() {
        try {
            val userId = authRepository.getCurrentUserId() ?: return
            
            // For now, disable realtime to avoid API compatibility issues
            // This can be implemented later with the correct Supabase realtime API
            Timber.d("Realtime updates not implemented yet for user: $userId")
        } catch (e: Exception) {
            Timber.e(e, "Failed to start real-time updates")
        }
    }

    suspend fun stopRealtimeUpdates() {
        try {
            realtimeChannel?.let { channel ->
                supabaseClient.realtime.removeChannel(channel)
                realtimeChannel = null
            }
            Timber.d("Stopped real-time updates")
        } catch (e: Exception) {
            Timber.e(e, "Failed to stop real-time updates")
        }
    }
}