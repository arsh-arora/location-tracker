package com.arshtraders.fieldtracker.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.arshtraders.fieldtracker.data.database.entities.AuditLogEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AuditLogDao_Impl implements AuditLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AuditLogEntity> __insertionAdapterOfAuditLogEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOldAuditLogs;

  public AuditLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAuditLogEntity = new EntityInsertionAdapter<AuditLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `audit_logs` (`id`,`userId`,`action`,`entityType`,`entityId`,`details`,`timestamp`,`deviceInfo`,`ipAddress`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AuditLogEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindString(3, entity.getAction());
        statement.bindString(4, entity.getEntityType());
        if (entity.getEntityId() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getEntityId());
        }
        if (entity.getDetails() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDetails());
        }
        statement.bindLong(7, entity.getTimestamp());
        if (entity.getDeviceInfo() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getDeviceInfo());
        }
        if (entity.getIpAddress() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getIpAddress());
        }
      }
    };
    this.__preparedStmtOfDeleteOldAuditLogs = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM audit_logs WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAuditLog(final AuditLogEntity auditLog,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAuditLogEntity.insert(auditLog);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOldAuditLogs(final long cutoffTime,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOldAuditLogs.acquire();
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
          __preparedStmtOfDeleteOldAuditLogs.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAuditLogs(final int limit, final int offset,
      final Continuation<? super List<AuditLogWithUser>> $completion) {
    final String _sql = "\n"
            + "        SELECT al.*, u.name as userName, u.email as userEmail \n"
            + "        FROM audit_logs al \n"
            + "        LEFT JOIN users u ON al.userId = u.id \n"
            + "        ORDER BY al.timestamp DESC \n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 2;
    _statement.bindLong(_argIndex, offset);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditLogWithUser>>() {
      @Override
      @NonNull
      public List<AuditLogWithUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntityType = CursorUtil.getColumnIndexOrThrow(_cursor, "entityType");
          final int _cursorIndexOfEntityId = CursorUtil.getColumnIndexOrThrow(_cursor, "entityId");
          final int _cursorIndexOfDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "details");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfIpAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "ipAddress");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
          final List<AuditLogWithUser> _result = new ArrayList<AuditLogWithUser>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditLogWithUser _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final String _tmpEntityType;
            _tmpEntityType = _cursor.getString(_cursorIndexOfEntityType);
            final String _tmpEntityId;
            if (_cursor.isNull(_cursorIndexOfEntityId)) {
              _tmpEntityId = null;
            } else {
              _tmpEntityId = _cursor.getString(_cursorIndexOfEntityId);
            }
            final String _tmpDetails;
            if (_cursor.isNull(_cursorIndexOfDetails)) {
              _tmpDetails = null;
            } else {
              _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final String _tmpIpAddress;
            if (_cursor.isNull(_cursorIndexOfIpAddress)) {
              _tmpIpAddress = null;
            } else {
              _tmpIpAddress = _cursor.getString(_cursorIndexOfIpAddress);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item = new AuditLogWithUser(_tmpId,_tmpUserId,_tmpAction,_tmpEntityType,_tmpEntityId,_tmpDetails,_tmpTimestamp,_tmpDeviceInfo,_tmpIpAddress,_tmpUserName,_tmpUserEmail);
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
  public Object getUserAuditLogs(final String userId, final int limit, final int offset,
      final Continuation<? super List<AuditLogWithUser>> $completion) {
    final String _sql = "\n"
            + "        SELECT al.*, u.name as userName, u.email as userEmail \n"
            + "        FROM audit_logs al \n"
            + "        LEFT JOIN users u ON al.userId = u.id \n"
            + "        WHERE al.userId = ? \n"
            + "        ORDER BY al.timestamp DESC \n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 3;
    _statement.bindLong(_argIndex, offset);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditLogWithUser>>() {
      @Override
      @NonNull
      public List<AuditLogWithUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntityType = CursorUtil.getColumnIndexOrThrow(_cursor, "entityType");
          final int _cursorIndexOfEntityId = CursorUtil.getColumnIndexOrThrow(_cursor, "entityId");
          final int _cursorIndexOfDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "details");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfIpAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "ipAddress");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
          final List<AuditLogWithUser> _result = new ArrayList<AuditLogWithUser>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditLogWithUser _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final String _tmpEntityType;
            _tmpEntityType = _cursor.getString(_cursorIndexOfEntityType);
            final String _tmpEntityId;
            if (_cursor.isNull(_cursorIndexOfEntityId)) {
              _tmpEntityId = null;
            } else {
              _tmpEntityId = _cursor.getString(_cursorIndexOfEntityId);
            }
            final String _tmpDetails;
            if (_cursor.isNull(_cursorIndexOfDetails)) {
              _tmpDetails = null;
            } else {
              _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final String _tmpIpAddress;
            if (_cursor.isNull(_cursorIndexOfIpAddress)) {
              _tmpIpAddress = null;
            } else {
              _tmpIpAddress = _cursor.getString(_cursorIndexOfIpAddress);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item = new AuditLogWithUser(_tmpId,_tmpUserId,_tmpAction,_tmpEntityType,_tmpEntityId,_tmpDetails,_tmpTimestamp,_tmpDeviceInfo,_tmpIpAddress,_tmpUserName,_tmpUserEmail);
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
  public Object getAuditLogsByAction(final String action, final int limit, final int offset,
      final Continuation<? super List<AuditLogWithUser>> $completion) {
    final String _sql = "\n"
            + "        SELECT al.*, u.name as userName, u.email as userEmail \n"
            + "        FROM audit_logs al \n"
            + "        LEFT JOIN users u ON al.userId = u.id \n"
            + "        WHERE al.action = ? \n"
            + "        ORDER BY al.timestamp DESC \n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, action);
    _argIndex = 2;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 3;
    _statement.bindLong(_argIndex, offset);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditLogWithUser>>() {
      @Override
      @NonNull
      public List<AuditLogWithUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntityType = CursorUtil.getColumnIndexOrThrow(_cursor, "entityType");
          final int _cursorIndexOfEntityId = CursorUtil.getColumnIndexOrThrow(_cursor, "entityId");
          final int _cursorIndexOfDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "details");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfIpAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "ipAddress");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
          final List<AuditLogWithUser> _result = new ArrayList<AuditLogWithUser>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditLogWithUser _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final String _tmpEntityType;
            _tmpEntityType = _cursor.getString(_cursorIndexOfEntityType);
            final String _tmpEntityId;
            if (_cursor.isNull(_cursorIndexOfEntityId)) {
              _tmpEntityId = null;
            } else {
              _tmpEntityId = _cursor.getString(_cursorIndexOfEntityId);
            }
            final String _tmpDetails;
            if (_cursor.isNull(_cursorIndexOfDetails)) {
              _tmpDetails = null;
            } else {
              _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final String _tmpIpAddress;
            if (_cursor.isNull(_cursorIndexOfIpAddress)) {
              _tmpIpAddress = null;
            } else {
              _tmpIpAddress = _cursor.getString(_cursorIndexOfIpAddress);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item = new AuditLogWithUser(_tmpId,_tmpUserId,_tmpAction,_tmpEntityType,_tmpEntityId,_tmpDetails,_tmpTimestamp,_tmpDeviceInfo,_tmpIpAddress,_tmpUserName,_tmpUserEmail);
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
  public Object getAuditLogsByDateRange(final long startTime, final long endTime, final int limit,
      final int offset, final Continuation<? super List<AuditLogWithUser>> $completion) {
    final String _sql = "\n"
            + "        SELECT al.*, u.name as userName, u.email as userEmail \n"
            + "        FROM audit_logs al \n"
            + "        LEFT JOIN users u ON al.userId = u.id \n"
            + "        WHERE al.timestamp BETWEEN ? AND ? \n"
            + "        ORDER BY al.timestamp DESC \n"
            + "        LIMIT ? OFFSET ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 4);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    _argIndex = 3;
    _statement.bindLong(_argIndex, limit);
    _argIndex = 4;
    _statement.bindLong(_argIndex, offset);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuditLogWithUser>>() {
      @Override
      @NonNull
      public List<AuditLogWithUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfEntityType = CursorUtil.getColumnIndexOrThrow(_cursor, "entityType");
          final int _cursorIndexOfEntityId = CursorUtil.getColumnIndexOrThrow(_cursor, "entityId");
          final int _cursorIndexOfDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "details");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeviceInfo = CursorUtil.getColumnIndexOrThrow(_cursor, "deviceInfo");
          final int _cursorIndexOfIpAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "ipAddress");
          final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
          final int _cursorIndexOfUserEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "userEmail");
          final List<AuditLogWithUser> _result = new ArrayList<AuditLogWithUser>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AuditLogWithUser _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpUserId;
            _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            final String _tmpAction;
            _tmpAction = _cursor.getString(_cursorIndexOfAction);
            final String _tmpEntityType;
            _tmpEntityType = _cursor.getString(_cursorIndexOfEntityType);
            final String _tmpEntityId;
            if (_cursor.isNull(_cursorIndexOfEntityId)) {
              _tmpEntityId = null;
            } else {
              _tmpEntityId = _cursor.getString(_cursorIndexOfEntityId);
            }
            final String _tmpDetails;
            if (_cursor.isNull(_cursorIndexOfDetails)) {
              _tmpDetails = null;
            } else {
              _tmpDetails = _cursor.getString(_cursorIndexOfDetails);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpDeviceInfo;
            if (_cursor.isNull(_cursorIndexOfDeviceInfo)) {
              _tmpDeviceInfo = null;
            } else {
              _tmpDeviceInfo = _cursor.getString(_cursorIndexOfDeviceInfo);
            }
            final String _tmpIpAddress;
            if (_cursor.isNull(_cursorIndexOfIpAddress)) {
              _tmpIpAddress = null;
            } else {
              _tmpIpAddress = _cursor.getString(_cursorIndexOfIpAddress);
            }
            final String _tmpUserName;
            if (_cursor.isNull(_cursorIndexOfUserName)) {
              _tmpUserName = null;
            } else {
              _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
            }
            final String _tmpUserEmail;
            if (_cursor.isNull(_cursorIndexOfUserEmail)) {
              _tmpUserEmail = null;
            } else {
              _tmpUserEmail = _cursor.getString(_cursorIndexOfUserEmail);
            }
            _item = new AuditLogWithUser(_tmpId,_tmpUserId,_tmpAction,_tmpEntityType,_tmpEntityId,_tmpDetails,_tmpTimestamp,_tmpDeviceInfo,_tmpIpAddress,_tmpUserName,_tmpUserEmail);
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
  public Object getAuditLogCount(final long startTime, final long endTime,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) \n"
            + "        FROM audit_logs \n"
            + "        WHERE timestamp BETWEEN ? AND ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
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
