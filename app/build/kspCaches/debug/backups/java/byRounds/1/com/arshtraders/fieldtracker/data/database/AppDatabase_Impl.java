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
import com.arshtraders.fieldtracker.data.database.dao.AuditLogDao;
import com.arshtraders.fieldtracker.data.database.dao.AuditLogDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao;
import com.arshtraders.fieldtracker.data.database.dao.LocationDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao;
import com.arshtraders.fieldtracker.data.database.dao.PlaceDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao;
import com.arshtraders.fieldtracker.data.database.dao.PunchDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.TeamDao;
import com.arshtraders.fieldtracker.data.database.dao.TeamDao_Impl;
import com.arshtraders.fieldtracker.data.database.dao.UserDao;
import com.arshtraders.fieldtracker.data.database.dao.UserDao_Impl;
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

  private volatile UserDao _userDao;

  private volatile TeamDao _teamDao;

  private volatile AuditLogDao _auditLogDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `location_points` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `deviceId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `accuracy` REAL NOT NULL, `speed` REAL, `provider` TEXT NOT NULL, `isMockLocation` INTEGER NOT NULL, `batteryLevel` INTEGER, `isUploaded` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `punches` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `placeId` TEXT, `type` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `accuracy` REAL NOT NULL, `serverDistance` REAL, `evidence` TEXT, `isUploaded` INTEGER NOT NULL, `syncStatus` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `places` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `radius` INTEGER NOT NULL, `status` TEXT NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `formattedAddress` TEXT, `placeType` TEXT, `locality` TEXT, `subLocality` TEXT, `thoroughfare` TEXT, `featureName` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`id` TEXT NOT NULL, `email` TEXT NOT NULL, `name` TEXT NOT NULL, `role` TEXT NOT NULL, `profilePictureUrl` TEXT, `phoneNumber` TEXT, `department` TEXT, `employeeId` TEXT, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, `lastLoginAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `teams` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `description` TEXT, `managerId` TEXT NOT NULL, `createdBy` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `updatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `team_members` (`teamId` TEXT NOT NULL, `userId` TEXT NOT NULL, `role` TEXT NOT NULL, `joinedAt` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`teamId`, `userId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `audit_logs` (`id` TEXT NOT NULL, `userId` TEXT NOT NULL, `action` TEXT NOT NULL, `entityType` TEXT NOT NULL, `entityId` TEXT, `details` TEXT, `timestamp` INTEGER NOT NULL, `deviceInfo` TEXT, `ipAddress` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2e45835eaa5b59fdf793cb992e65872b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `location_points`");
        db.execSQL("DROP TABLE IF EXISTS `punches`");
        db.execSQL("DROP TABLE IF EXISTS `places`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `teams`");
        db.execSQL("DROP TABLE IF EXISTS `team_members`");
        db.execSQL("DROP TABLE IF EXISTS `audit_logs`");
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
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(12);
        _columnsUsers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profilePictureUrl", new TableInfo.Column("profilePictureUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phoneNumber", new TableInfo.Column("phoneNumber", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("department", new TableInfo.Column("department", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("employeeId", new TableInfo.Column("employeeId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastLoginAt", new TableInfo.Column("lastLoginAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.arshtraders.fieldtracker.data.database.entities.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsTeams = new HashMap<String, TableInfo.Column>(8);
        _columnsTeams.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("managerId", new TableInfo.Column("managerId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("createdBy", new TableInfo.Column("createdBy", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeams = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeams = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeams = new TableInfo("teams", _columnsTeams, _foreignKeysTeams, _indicesTeams);
        final TableInfo _existingTeams = TableInfo.read(db, "teams");
        if (!_infoTeams.equals(_existingTeams)) {
          return new RoomOpenHelper.ValidationResult(false, "teams(com.arshtraders.fieldtracker.data.database.entities.TeamEntity).\n"
                  + " Expected:\n" + _infoTeams + "\n"
                  + " Found:\n" + _existingTeams);
        }
        final HashMap<String, TableInfo.Column> _columnsTeamMembers = new HashMap<String, TableInfo.Column>(5);
        _columnsTeamMembers.put("teamId", new TableInfo.Column("teamId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeamMembers.put("userId", new TableInfo.Column("userId", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeamMembers.put("role", new TableInfo.Column("role", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeamMembers.put("joinedAt", new TableInfo.Column("joinedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeamMembers.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeamMembers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeamMembers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeamMembers = new TableInfo("team_members", _columnsTeamMembers, _foreignKeysTeamMembers, _indicesTeamMembers);
        final TableInfo _existingTeamMembers = TableInfo.read(db, "team_members");
        if (!_infoTeamMembers.equals(_existingTeamMembers)) {
          return new RoomOpenHelper.ValidationResult(false, "team_members(com.arshtraders.fieldtracker.data.database.entities.TeamMemberEntity).\n"
                  + " Expected:\n" + _infoTeamMembers + "\n"
                  + " Found:\n" + _existingTeamMembers);
        }
        final HashMap<String, TableInfo.Column> _columnsAuditLogs = new HashMap<String, TableInfo.Column>(9);
        _columnsAuditLogs.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("userId", new TableInfo.Column("userId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("action", new TableInfo.Column("action", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("entityType", new TableInfo.Column("entityType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("entityId", new TableInfo.Column("entityId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("details", new TableInfo.Column("details", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("deviceInfo", new TableInfo.Column("deviceInfo", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuditLogs.put("ipAddress", new TableInfo.Column("ipAddress", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAuditLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAuditLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAuditLogs = new TableInfo("audit_logs", _columnsAuditLogs, _foreignKeysAuditLogs, _indicesAuditLogs);
        final TableInfo _existingAuditLogs = TableInfo.read(db, "audit_logs");
        if (!_infoAuditLogs.equals(_existingAuditLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "audit_logs(com.arshtraders.fieldtracker.data.database.entities.AuditLogEntity).\n"
                  + " Expected:\n" + _infoAuditLogs + "\n"
                  + " Found:\n" + _existingAuditLogs);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2e45835eaa5b59fdf793cb992e65872b", "cca48c1cb46e044b2efab6f4d5150da9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "location_points","punches","places","users","teams","team_members","audit_logs");
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
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `teams`");
      _db.execSQL("DELETE FROM `team_members`");
      _db.execSQL("DELETE FROM `audit_logs`");
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
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TeamDao.class, TeamDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AuditLogDao.class, AuditLogDao_Impl.getRequiredConverters());
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

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public TeamDao teamDao() {
    if (_teamDao != null) {
      return _teamDao;
    } else {
      synchronized(this) {
        if(_teamDao == null) {
          _teamDao = new TeamDao_Impl(this);
        }
        return _teamDao;
      }
    }
  }

  @Override
  public AuditLogDao auditLogDao() {
    if (_auditLogDao != null) {
      return _auditLogDao;
    } else {
      synchronized(this) {
        if(_auditLogDao == null) {
          _auditLogDao = new AuditLogDao_Impl(this);
        }
        return _auditLogDao;
      }
    }
  }
}
