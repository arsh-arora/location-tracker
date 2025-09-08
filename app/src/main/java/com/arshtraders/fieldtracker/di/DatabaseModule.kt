package com.arshtraders.fieldtracker.di

import android.content.Context
import androidx.room.Room
import com.arshtraders.fieldtracker.data.database.AppDatabase
import com.arshtraders.fieldtracker.data.database.dao.LocationDao
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao
import com.arshtraders.fieldtracker.data.database.dao.PunchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration() // Use destructive migration during development
        .build()
    }
    
    @Provides
    fun provideLocationDao(database: AppDatabase): LocationDao = database.locationDao()
    
    @Provides
    fun providePunchDao(database: AppDatabase): PunchDao = database.punchDao()
    
    @Provides
    fun providePlaceDao(database: AppDatabase): PlaceDao = database.placeDao()
}