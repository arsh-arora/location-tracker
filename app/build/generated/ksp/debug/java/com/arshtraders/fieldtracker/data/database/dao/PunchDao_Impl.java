package com.arshtraders.fieldtracker.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.arshtraders.fieldtracker.data.database.entities.PunchEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PunchDao_Impl implements PunchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PunchEntity> __insertionAdapterOfPunchEntity;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsUploaded;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldUploadedPunches;

  public PunchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPunchEntity = new EntityInsertionAdapter<PunchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `punches` (`id`,`userId`,`placeId`,`type`,`timestamp`,`latitude`,`longitude`,`accuracy`,`serverDistance`,`evidence`,`isUploaded`,`syncStatus`,`createdAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PunchEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        if (entity.getPlaceId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getPlaceId());
        }
        statement.bindString(4, entity.getType());
        statement.bindLong(5, entity.getTimestamp());
        statement.bindDouble(6, entity.getLatitude());
        statement.bindDouble(7, entity.getLongitude());
        statement.bindDouble(8, entity.getAccuracy());
        if (entity.getServerDistance() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getServerDistance());
        }
        if (entity.getEvidence() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getEvidence());
        }
        final int _tmp = entity.isUploaded() ? 1 : 0;
        statement.bindLong(11, _tmp);
        statement.bindString(12, entity.getSyncStatus());
        statement.bindLong(13, entity.getCreatedAt());
      }
    };
    this.__preparedStmtOfMarkAsUploaded = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE punches SET isUploaded = 1, syncStatus = 'SYNCED' WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOldUploadedPunches = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM punches WHERE isUploaded = 1 AND timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPunch(final PunchEntity punch, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPunchEntity.insert(punch);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object markAsUploaded(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsUploaded.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsUploaded.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldUploadedPunches(final long cutoffTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldUploadedPunches.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cutoffTime);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOldUploadedPunches.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getPendingPunches(final Continuation<? super List<PunchEntity>> $completion) {
    final String _sql = "SELECT * FROM punches WHERE isUploaded = 0 ORDER BY timestamp ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PunchEntity>>() {
      @Override
      @NonNull
      public List<PunchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfServerDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "serverDistance");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfIsUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isUploaded");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PunchEntity> _result = new ArrayList<PunchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PunchEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPlaceId;
            if (_cursor.isNull(_cursorIndexOfPlaceId)) {
              _tmpPlaceId = null;
            } else {
              _tmpPlaceId = _cursor.getString(_cursorIndexOfPlaceId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final Float _tmpServerDistance;
            if (_cursor.isNull(_cursorIndexOfServerDistance)) {
              _tmpServerDistance = null;
            } else {
              _tmpServerDistance = _cursor.getFloat(_cursorIndexOfServerDistance);
            }
            final String _tmpEvidence;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmpEvidence = null;
            } else {
              _tmpEvidence = _cursor.getString(_cursorIndexOfEvidence);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PunchEntity(_tmpId,_tmpUserId,_tmpPlaceId,_tmpType,_tmpTimestamp,_tmpLatitude,_tmpLongitude,_tmpAccuracy,_tmpServerDistance,_tmpEvidence,_tmpIsUploaded,_tmpSyncStatus,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<PunchEntity>> getTodaysPunches() {
    final String _sql = "SELECT * FROM punches WHERE DATE(timestamp/1000, 'unixepoch', 'localtime') = DATE('now', 'localtime') ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"punches"}, new Callable<List<PunchEntity>>() {
      @Override
      @NonNull
      public List<PunchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfServerDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "serverDistance");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfIsUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isUploaded");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final List<PunchEntity> _result = new ArrayList<PunchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PunchEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPlaceId;
            if (_cursor.isNull(_cursorIndexOfPlaceId)) {
              _tmpPlaceId = null;
            } else {
              _tmpPlaceId = _cursor.getString(_cursorIndexOfPlaceId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final Float _tmpServerDistance;
            if (_cursor.isNull(_cursorIndexOfServerDistance)) {
              _tmpServerDistance = null;
            } else {
              _tmpServerDistance = _cursor.getFloat(_cursorIndexOfServerDistance);
            }
            final String _tmpEvidence;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmpEvidence = null;
            } else {
              _tmpEvidence = _cursor.getString(_cursorIndexOfEvidence);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new PunchEntity(_tmpId,_tmpUserId,_tmpPlaceId,_tmpType,_tmpTimestamp,_tmpLatitude,_tmpLongitude,_tmpAccuracy,_tmpServerDistance,_tmpEvidence,_tmpIsUploaded,_tmpSyncStatus,_tmpCreatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLastPunch(final Continuation<? super PunchEntity> $completion) {
    final String _sql = "SELECT * FROM punches ORDER BY timestamp DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PunchEntity>() {
      @Override
      @Nullable
      public PunchEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfPlaceId = CursorUtil.getColumnIndexOrThrow(_cursor, "placeId");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfAccuracy = CursorUtil.getColumnIndexOrThrow(_cursor, "accuracy");
          final int _cursorIndexOfServerDistance = CursorUtil.getColumnIndexOrThrow(_cursor, "serverDistance");
          final int _cursorIndexOfEvidence = CursorUtil.getColumnIndexOrThrow(_cursor, "evidence");
          final int _cursorIndexOfIsUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "isUploaded");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "syncStatus");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final PunchEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPlaceId;
            if (_cursor.isNull(_cursorIndexOfPlaceId)) {
              _tmpPlaceId = null;
            } else {
              _tmpPlaceId = _cursor.getString(_cursorIndexOfPlaceId);
            }
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final Float _tmpServerDistance;
            if (_cursor.isNull(_cursorIndexOfServerDistance)) {
              _tmpServerDistance = null;
            } else {
              _tmpServerDistance = _cursor.getFloat(_cursorIndexOfServerDistance);
            }
            final String _tmpEvidence;
            if (_cursor.isNull(_cursorIndexOfEvidence)) {
              _tmpEvidence = null;
            } else {
              _tmpEvidence = _cursor.getString(_cursorIndexOfEvidence);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new PunchEntity(_tmpId,_tmpUserId,_tmpPlaceId,_tmpType,_tmpTimestamp,_tmpLatitude,_tmpLongitude,_tmpAccuracy,_tmpServerDistance,_tmpEvidence,_tmpIsUploaded,_tmpSyncStatus,_tmpCreatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getLastPunchWithPlace(final Continuation<? super PunchWithPlace> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            p.id,\n"
            + "            p.userId,\n"
            + "            p.placeId,\n"
            + "            p.type,\n"
            + "            p.timestamp,\n"
            + "            p.latitude,\n"
            + "            p.longitude,\n"
            + "            p.accuracy,\n"
            + "            p.serverDistance,\n"
            + "            p.isUploaded,\n"
            + "            p.syncStatus,\n"
            + "            pl.name as placeName,\n"
            + "            pl.formattedAddress as placeAddress\n"
            + "        FROM punches p \n"
            + "        LEFT JOIN places pl ON p.placeId = pl.id \n"
            + "        ORDER BY p.timestamp DESC \n"
            + "        LIMIT 1\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PunchWithPlace>() {
      @Override
      @Nullable
      public PunchWithPlace call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfUserId = 1;
          final int _cursorIndexOfPlaceId = 2;
          final int _cursorIndexOfType = 3;
          final int _cursorIndexOfTimestamp = 4;
          final int _cursorIndexOfLatitude = 5;
          final int _cursorIndexOfLongitude = 6;
          final int _cursorIndexOfAccuracy = 7;
          final int _cursorIndexOfServerDistance = 8;
          final int _cursorIndexOfIsUploaded = 9;
          final int _cursorIndexOfSyncStatus = 10;
          final int _cursorIndexOfPlaceName = 11;
          final int _cursorIndexOfPlaceAddress = 12;
          final PunchWithPlace _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpPlaceId;
            _tmpPlaceId = _cursor.getString(_cursorIndexOfPlaceId);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final float _tmpAccuracy;
            _tmpAccuracy = _cursor.getFloat(_cursorIndexOfAccuracy);
            final Float _tmpServerDistance;
            if (_cursor.isNull(_cursorIndexOfServerDistance)) {
              _tmpServerDistance = null;
            } else {
              _tmpServerDistance = _cursor.getFloat(_cursorIndexOfServerDistance);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final String _tmpPlaceName;
            if (_cursor.isNull(_cursorIndexOfPlaceName)) {
              _tmpPlaceName = null;
            } else {
              _tmpPlaceName = _cursor.getString(_cursorIndexOfPlaceName);
            }
            final String _tmpPlaceAddress;
            if (_cursor.isNull(_cursorIndexOfPlaceAddress)) {
              _tmpPlaceAddress = null;
            } else {
              _tmpPlaceAddress = _cursor.getString(_cursorIndexOfPlaceAddress);
            }
            _result = new PunchWithPlace(_tmpId,_tmpUserId,_tmpPlaceId,_tmpType,_tmpTimestamp,_tmpLatitude,_tmpLongitude,_tmpAccuracy,_tmpServerDistance,_tmpIsUploaded,_tmpSyncStatus,_tmpPlaceName,_tmpPlaceAddress);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
