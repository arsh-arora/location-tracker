package com.arshtraders.fieldtracker.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.arshtraders.fieldtracker.data.database.entities.PlaceEntity;
import java.lang.Class;
import java.lang.Exception;
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
public final class PlaceDao_Impl implements PlaceDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlaceEntity> __insertionAdapterOfPlaceEntity;

  private final EntityDeletionOrUpdateAdapter<PlaceEntity> __updateAdapterOfPlaceEntity;

  public PlaceDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlaceEntity = new EntityInsertionAdapter<PlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `places` (`id`,`name`,`latitude`,`longitude`,`radius`,`status`,`createdAt`,`updatedAt`,`formattedAddress`,`placeType`,`locality`,`subLocality`,`thoroughfare`,`featureName`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindDouble(3, entity.getLatitude());
        statement.bindDouble(4, entity.getLongitude());
        statement.bindLong(5, entity.getRadius());
        statement.bindString(6, entity.getStatus());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        if (entity.getFormattedAddress() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getFormattedAddress());
        }
        if (entity.getPlaceType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPlaceType());
        }
        if (entity.getLocality() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLocality());
        }
        if (entity.getSubLocality() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getSubLocality());
        }
        if (entity.getThoroughfare() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getThoroughfare());
        }
        if (entity.getFeatureName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFeatureName());
        }
      }
    };
    this.__updateAdapterOfPlaceEntity = new EntityDeletionOrUpdateAdapter<PlaceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `places` SET `id` = ?,`name` = ?,`latitude` = ?,`longitude` = ?,`radius` = ?,`status` = ?,`createdAt` = ?,`updatedAt` = ?,`formattedAddress` = ?,`placeType` = ?,`locality` = ?,`subLocality` = ?,`thoroughfare` = ?,`featureName` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PlaceEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindDouble(3, entity.getLatitude());
        statement.bindDouble(4, entity.getLongitude());
        statement.bindLong(5, entity.getRadius());
        statement.bindString(6, entity.getStatus());
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getUpdatedAt());
        if (entity.getFormattedAddress() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getFormattedAddress());
        }
        if (entity.getPlaceType() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPlaceType());
        }
        if (entity.getLocality() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLocality());
        }
        if (entity.getSubLocality() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getSubLocality());
        }
        if (entity.getThoroughfare() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getThoroughfare());
        }
        if (entity.getFeatureName() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getFeatureName());
        }
        statement.bindString(15, entity.getId());
      }
    };
  }

  @Override
  public Object insertPlace(final PlaceEntity place, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlaceEntity.insert(place);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updatePlace(final PlaceEntity place, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlaceEntity.handle(place);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getActivePlaces(final Continuation<? super List<PlaceEntity>> $completion) {
    final String _sql = "SELECT * FROM places WHERE status = 'ACTIVE'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<PlaceEntity>>() {
      @Override
      @NonNull
      public List<PlaceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRadius = CursorUtil.getColumnIndexOrThrow(_cursor, "radius");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfFormattedAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "formattedAddress");
          final int _cursorIndexOfPlaceType = CursorUtil.getColumnIndexOrThrow(_cursor, "placeType");
          final int _cursorIndexOfLocality = CursorUtil.getColumnIndexOrThrow(_cursor, "locality");
          final int _cursorIndexOfSubLocality = CursorUtil.getColumnIndexOrThrow(_cursor, "subLocality");
          final int _cursorIndexOfThoroughfare = CursorUtil.getColumnIndexOrThrow(_cursor, "thoroughfare");
          final int _cursorIndexOfFeatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "featureName");
          final List<PlaceEntity> _result = new ArrayList<PlaceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PlaceEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final int _tmpRadius;
            _tmpRadius = _cursor.getInt(_cursorIndexOfRadius);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpFormattedAddress;
            if (_cursor.isNull(_cursorIndexOfFormattedAddress)) {
              _tmpFormattedAddress = null;
            } else {
              _tmpFormattedAddress = _cursor.getString(_cursorIndexOfFormattedAddress);
            }
            final String _tmpPlaceType;
            if (_cursor.isNull(_cursorIndexOfPlaceType)) {
              _tmpPlaceType = null;
            } else {
              _tmpPlaceType = _cursor.getString(_cursorIndexOfPlaceType);
            }
            final String _tmpLocality;
            if (_cursor.isNull(_cursorIndexOfLocality)) {
              _tmpLocality = null;
            } else {
              _tmpLocality = _cursor.getString(_cursorIndexOfLocality);
            }
            final String _tmpSubLocality;
            if (_cursor.isNull(_cursorIndexOfSubLocality)) {
              _tmpSubLocality = null;
            } else {
              _tmpSubLocality = _cursor.getString(_cursorIndexOfSubLocality);
            }
            final String _tmpThoroughfare;
            if (_cursor.isNull(_cursorIndexOfThoroughfare)) {
              _tmpThoroughfare = null;
            } else {
              _tmpThoroughfare = _cursor.getString(_cursorIndexOfThoroughfare);
            }
            final String _tmpFeatureName;
            if (_cursor.isNull(_cursorIndexOfFeatureName)) {
              _tmpFeatureName = null;
            } else {
              _tmpFeatureName = _cursor.getString(_cursorIndexOfFeatureName);
            }
            _item = new PlaceEntity(_tmpId,_tmpName,_tmpLatitude,_tmpLongitude,_tmpRadius,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpFormattedAddress,_tmpPlaceType,_tmpLocality,_tmpSubLocality,_tmpThoroughfare,_tmpFeatureName);
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
  public Object getPlaceById(final String placeId,
      final Continuation<? super PlaceEntity> $completion) {
    final String _sql = "SELECT * FROM places WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, placeId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PlaceEntity>() {
      @Override
      @Nullable
      public PlaceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfRadius = CursorUtil.getColumnIndexOrThrow(_cursor, "radius");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfFormattedAddress = CursorUtil.getColumnIndexOrThrow(_cursor, "formattedAddress");
          final int _cursorIndexOfPlaceType = CursorUtil.getColumnIndexOrThrow(_cursor, "placeType");
          final int _cursorIndexOfLocality = CursorUtil.getColumnIndexOrThrow(_cursor, "locality");
          final int _cursorIndexOfSubLocality = CursorUtil.getColumnIndexOrThrow(_cursor, "subLocality");
          final int _cursorIndexOfThoroughfare = CursorUtil.getColumnIndexOrThrow(_cursor, "thoroughfare");
          final int _cursorIndexOfFeatureName = CursorUtil.getColumnIndexOrThrow(_cursor, "featureName");
          final PlaceEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpLatitude;
            _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            final double _tmpLongitude;
            _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            final int _tmpRadius;
            _tmpRadius = _cursor.getInt(_cursorIndexOfRadius);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final String _tmpFormattedAddress;
            if (_cursor.isNull(_cursorIndexOfFormattedAddress)) {
              _tmpFormattedAddress = null;
            } else {
              _tmpFormattedAddress = _cursor.getString(_cursorIndexOfFormattedAddress);
            }
            final String _tmpPlaceType;
            if (_cursor.isNull(_cursorIndexOfPlaceType)) {
              _tmpPlaceType = null;
            } else {
              _tmpPlaceType = _cursor.getString(_cursorIndexOfPlaceType);
            }
            final String _tmpLocality;
            if (_cursor.isNull(_cursorIndexOfLocality)) {
              _tmpLocality = null;
            } else {
              _tmpLocality = _cursor.getString(_cursorIndexOfLocality);
            }
            final String _tmpSubLocality;
            if (_cursor.isNull(_cursorIndexOfSubLocality)) {
              _tmpSubLocality = null;
            } else {
              _tmpSubLocality = _cursor.getString(_cursorIndexOfSubLocality);
            }
            final String _tmpThoroughfare;
            if (_cursor.isNull(_cursorIndexOfThoroughfare)) {
              _tmpThoroughfare = null;
            } else {
              _tmpThoroughfare = _cursor.getString(_cursorIndexOfThoroughfare);
            }
            final String _tmpFeatureName;
            if (_cursor.isNull(_cursorIndexOfFeatureName)) {
              _tmpFeatureName = null;
            } else {
              _tmpFeatureName = _cursor.getString(_cursorIndexOfFeatureName);
            }
            _result = new PlaceEntity(_tmpId,_tmpName,_tmpLatitude,_tmpLongitude,_tmpRadius,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpFormattedAddress,_tmpPlaceType,_tmpLocality,_tmpSubLocality,_tmpThoroughfare,_tmpFeatureName);
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
