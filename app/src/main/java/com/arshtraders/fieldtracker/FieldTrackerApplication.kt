package com.arshtraders.fieldtracker

import android.app.Application
import com.arshtraders.fieldtracker.sync.SyncManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class FieldTrackerApplication : Application() {
    
    @Inject lateinit var syncManager: SyncManager
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("FieldTracker Application Started")
        
        // Schedule periodic sync for offline data
        syncManager.schedulePeriodicSync()
        Timber.d("Periodic sync scheduled")
    }
}