package com.arshtraders.fieldtracker.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arshtraders.fieldtracker.data.database.dao.LocationDao
import com.arshtraders.fieldtracker.data.database.dao.PunchDao
import com.arshtraders.fieldtracker.data.network.ApiService
import com.arshtraders.fieldtracker.data.network.dto.LocationBatchRequest
import com.arshtraders.fieldtracker.data.network.dto.PunchUploadRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val locationDao: LocationDao,
    private val punchDao: PunchDao,
    private val apiService: ApiService
) : CoroutineWorker(context, params) {
    
    companion object {
        const val WORK_NAME = "sync_worker"
        const val BATCH_SIZE = 500
    }
    
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Timber.d("Starting sync")
            
            val locationsSynced = syncLocations()
            val punchesSynced = syncPunches()
            
            Timber.d("Sync completed. Locations: $locationsSynced, Punches: $punchesSynced")
            
            Result.success()
        } catch (e: Exception) {
            Timber.e(e, "Sync failed")
            
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }
    
    private suspend fun syncLocations(): Int {
        var totalSynced = 0
        
        while (true) {
            val pendingLocations = locationDao.getPendingUploads(BATCH_SIZE)
            if (pendingLocations.isEmpty()) break
            
            val request = LocationBatchRequest(
                userId = getUserId(),
                deviceId = getDeviceId(),
                points = pendingLocations.map { location ->
                    LocationBatchRequest.LocationPoint(
                        timestamp = location.timestamp,
                        latitude = location.latitude,
                        longitude = location.longitude,
                        accuracy = location.accuracy,
                        speed = location.speed,
                        provider = location.provider,
                        isMockLocation = location.isMockLocation
                    )
                }
            )
            
            val response = apiService.uploadLocations(request)
            
            if (response.isSuccessful) {
                locationDao.markAsUploaded(pendingLocations.map { it.id })
                totalSynced += pendingLocations.size
            } else {
                throw Exception("Failed to upload locations: ${response.code()}")
            }
        }
        
        return totalSynced
    }
    
    private suspend fun syncPunches(): Int {
        var totalSynced = 0
        val pendingPunches = punchDao.getPendingPunches()
        
        for (punch in pendingPunches) {
            val request = PunchUploadRequest(
                userId = getUserId(),
                placeId = punch.placeId,
                type = punch.type,
                timestamp = punch.timestamp,
                latitude = punch.latitude,
                longitude = punch.longitude,
                accuracy = punch.accuracy
            )
            
            val response = apiService.uploadPunch(request)
            
            if (response.isSuccessful) {
                punchDao.markAsUploaded(punch.id)
                totalSynced++
            } else {
                Timber.e("Failed to upload punch ${punch.id}")
            }
        }
        
        return totalSynced
    }
    
    private fun getUserId(): String = "user_123"
    private fun getDeviceId(): String = "device_123"
}