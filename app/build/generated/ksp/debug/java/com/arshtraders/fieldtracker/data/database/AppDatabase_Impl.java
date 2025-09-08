package com.arshtraders.fieldtracker.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao;
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile LocationDao _locationDao;

  private volatile PunchDao _punchDao;

  private volatile PlaceDao _placeDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `location_points` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `deviceId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `accuracy` REAL NOT NULL, `speed` REAL, `provider` TEXT NOT NULL, `isMockLocation` INTEGER NOT NULL, `batteryLevel` INTEGER, `isUploaded` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `punches` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `placeId` TEXT, `type` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `accuracy` REAL NOT NULL, `serverDistance` REAL, `evidence` TEXT, `isUploaded` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `places` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `radius` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `formattedAddress` TEXT, `placeType` TEXT, `locality` TEXT, `subLocality` TEXT, `thoroughfare` TEXT, `featureName` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ac732b0551bbbe92b728c33bff4c6f7d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `location_points`");
        db.execSQL("DROP TABLE IF EXISTS `punches`");
        db.execSQL("DROP TABLE IF EXISTS `places`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsLocationPoints = new HashMap<String, TableInfo.Column>(13);
        _columnsLocationPoints.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("deviceId", new TableInfo.Column("deviceId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("speed", new TableInfo.Column("speed", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("provider", new TableInfo.Column("provider", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("isMockLocation", new TableInfo.Column("isMockLocation", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("batteryLevel", new TableInfo.Column("batteryLevel", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("isUploaded", new TableInfo.Column("isUploaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLocationPoints.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLocationPoints = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLocationPoints = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLocationPoints = new TableInfo("location_points", _columnsLocationPoints, _foreignKeysLocationPoints, _indicesLocationPoints);
        final TableInfo _existingLocationPoints = TableInfo.read(db, "location_points");
        if (!_infoLocationPoints.equals(_existingLocationPoints)) {
          return new RoomOpenHelper.ValidationResult(false, "location_points(com.arshtraders.fieldtracker.data.database.entities.LocationPointEntity).\n"
                  + " Expected:\n" + _infoLocationPoints + "\n"
                  + " Found:\n" + _existingLocationPoints);
        }
        final HashMap<String, TableInfo.Column> _columnsPunches = new HashMap<String, TableInfo.Column>(13);
        _columnsPunches.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("placeId", new TableInfo.Column("placeId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("accuracy", new TableInfo.Column("accuracy", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("serverDistance", new TableInfo.Column("serverDistance", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("evidence", new TableInfo.Column("evidence", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("isUploaded", new TableInfo.Column("isUploaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("syncStatus", new TableInfo.Column("syncStatus", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPunches.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPunches = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPunches = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPunches = new TableInfo("punches", _columnsPunches, _foreignKeysPunches, _indicesPunches);
        final TableInfo _existingPunches = TableInfo.read(db, "punches");
        if (!_infoPunches.equals(_existingPunches)) {
          return new RoomOpenHelper.ValidationResult(false, "punches(com.arshtraders.fieldtracker.data.database.entities.PunchEntity).\n"
                  + " Expected:\n" + _infoPunches + "\n"
                  + " Found:\n" + _existingPunches);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaces = new HashMap<String, TableInfo.Column>(14);
        _columnsPlaces.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("radius", new TableInfo.Column("radius", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("formattedAddress", new TableInfo.Column("formattedAddress", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("placeType", new TableInfo.Column("placeType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("locality", new TableInfo.Column("locality", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("subLocality", new TableInfo.Column("subLocality", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("thoroughfare", new TableInfo.Column("thoroughfare", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlaces.put("featureName", new TableInfo.Column("featureName", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaces = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaces = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaces = new TableInfo("places", _columnsPlaces, _foreignKeysPlaces, _indicesPlaces);
        final TableInfo _existingPlaces = TableInfo.read(db, "places");
        if (!_infoPlaces.equals(_existingPlaces)) {
          return new RoomOpenHelper.ValidationResult(false, "places(com.arshtraders.fieldtracker.data.database.entities.PlaceEntity).\n"
                  + " Expected:\n" + _infoPlaces + "\n"
                  + " Found:\n" + _existingPlaces);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "ac732b0551bbbe92b728c33bff4c6f7d", "8ae2b03909417e45433f158565f4e1ea");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "location_points","punches","places");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `location_points`");
      _db.execSQL("DELETE FROM `punches`");
      _db.execSQL("DELETE FROM `places`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(LocationDao.class, LocationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PunchDao.class, PunchDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PlaceDao.class, PlaceDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public LocationDao locationDao() {
    if (_locationDao != null) {
      return _locationDao;
    } else {
      synchronized(this) {
        if(_locationDao == null) {
          _locationDao = new LocationDao_Impl(this);
        }
        return _locationDao;
      }
    }
  }

  @Override
  public PunchDao punchDao() {
    if (_punchDao != null) {
      return _punchDao;
    } else {
      synchronized(this) {
        if(_punchDao == null) {
          _punchDao = new PunchDao_Impl(this);
        }
        return _punchDao;
      }
    }
  }

  @Override
  public PlaceDao placeDao() {
    if (_placeDao != null) {
      return _placeDao;
    } else {
      synchronized(this) {
        if(_placeDao == null) {
          _placeDao = new PlaceDao_Impl(this);
        }
        return _placeDao;
      }
    }
  }
}
