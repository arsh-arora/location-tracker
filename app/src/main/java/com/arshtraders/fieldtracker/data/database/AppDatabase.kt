package com.arshtraders.fieldtracker.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.arshtraders.fieldtracker.data.database.dao.*
import com.arshtraders.fieldtracker.data.database.entities.*

@Database(
    entities = [
        LocationPointEntity::class,
        PunchEntity::class,
        PlaceEntity::class,
        UserEntity::class,
        TeamEntity::class,
        TeamMemberEntity::class,
        AuditLogEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
    abstract fun punchDao(): PunchDao
    abstract fun placeDao(): PlaceDao
    abstract fun userDao(): UserDao
    abstract fun teamDao(): TeamDao
    abstract fun auditLogDao(): AuditLogDao
    
    companion object {
        const val DATABASE_NAME = "fieldtracker_database"
        
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Add new address fields to places table
                db.execSQL("ALTER TABLE places ADD COLUMN formattedAddress TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN placeType TEXT")  
                db.execSQL("ALTER TABLE places ADD COLUMN locality TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN subLocality TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN thoroughfare TEXT")
                db.execSQL("ALTER TABLE places ADD COLUMN featureName TEXT")
            }
        }
    }
}