package org.telegram.ui.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.ui.database.model.AlarmResponse;
import org.telegram.ui.database.model.UpdateModel;

import java.util.ArrayList;
import java.util.List;

import org.telegram.messenger.BuildConfig;

public class DataBaseAccess {
    private MobogramDBHelper openHelper;

    public DataBaseAccess() {
        this.openHelper = ApplicationLoader.getOpenHelper();
    }

    public Long insertOrUpdateUpdate(UpdateModel update) {
        SQLiteDatabase writableDatabase = this.openHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            Long valueOf;
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConstants.UPDATE_COL_TYPE, Integer.valueOf(update.getType()));
            contentValues.put(DBConstants.UPDATE_COL_OLD_VALUE, update.getOldValue());
            contentValues.put(DBConstants.UPDATE_COL_NEW_VALUE, update.getNewValue());
            contentValues.put(DBConstants.UPDATE_COL_USER_ID, Integer.valueOf(update.getUserId()));
            contentValues.put(DBConstants.UPDATE_COL_IS_NEW, Integer.valueOf(update.isNew() ? 1 : 0));
            if (update.getChangeDate() != null) {
                contentValues.put(DBConstants.UPDATE_COL_CHANGE_DATE, update.getChangeDate());
            }
            if (update.getId() == null) {
                long result = writableDatabase.insertOrThrow(DBConstants.TABLE_UPDATE, null, contentValues);
                writableDatabase.setTransactionSuccessful();
                valueOf = Long.valueOf(result);
            } else {
                writableDatabase.update(DBConstants.TABLE_UPDATE, contentValues, "_id=" + update.getId().longValue(), null);
                writableDatabase.setTransactionSuccessful();
                valueOf = update.getId();
                writableDatabase.endTransaction();
            }
            return valueOf;
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public UpdateModel getUpdate(Long id) {
        List<UpdateModel> updateList = getUpdateList("_id=" + id);
        if (updateList.size() == 1) {
            return (UpdateModel) updateList.get(0);
        }
        return null;
    }

    public void markAllUpdatesAsRead() {
        SQLiteDatabase writableDatabase = this.openHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.putNull(DBConstants.UPDATE_COL_IS_NEW);
            writableDatabase.update(DBConstants.TABLE_UPDATE, contentValues, null, null);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public List<UpdateModel> getAllUpdates() {
        return getUpdateList(null);
    }

    public void deleteUpdate(Long updateId) {
        SQLiteDatabase writableDatabase = this.openHelper.getWritableDatabase();
        String whereClause = "_id = " + updateId;
        writableDatabase.beginTransaction();
        try {
            writableDatabase.delete(DBConstants.TABLE_UPDATE, whereClause, null);
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public List<UpdateModel> getNewUpdateList() {
        return getUpdateList("is_new=1");
    }

    public List<UpdateModel> getUpdateList(String whereClause) {
        SQLiteDatabase readableDatabase = this.openHelper.getReadableDatabase();
        Cursor cursor = null;
        List<UpdateModel> updates = new ArrayList();
        try {
            cursor = readableDatabase.query(DBConstants.TABLE_UPDATE, null, whereClause, null, null, null, DBConstants.ID);
            while (cursor.moveToNext()) {
                updates.add(getUpdateFromCursor(cursor));
            }
            return updates;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getUpdateListCount(int updateType) {
        String whereClause = null;
        if (updateType != 0) {
            whereClause = "type=" + updateType;
        }
        Cursor cursor = null;
        try {
            cursor = this.openHelper.getReadableDatabase().query(DBConstants.TABLE_UPDATE, null, whereClause, null, null, null, DBConstants.ID);
            int count = cursor.getCount();
            return count;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public int getNewUpdateListCount() {
        String whereClause = "is_new=1";
        Cursor cursor = null;
        try {
            cursor = this.openHelper.getReadableDatabase().query(DBConstants.TABLE_UPDATE, null, whereClause, null, null, null, DBConstants.ID);
            int count = cursor.getCount();
            return count;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Cursor getUpdateListCursor(int updateType, int limit) {
        String whereClause = null;
        if (updateType != 0) {
            whereClause = "type=" + updateType;
        }
        String str = limit + BuildConfig.FLAVOR;
        return this.openHelper.getReadableDatabase().query(DBConstants.TABLE_UPDATE, null, whereClause, null, null, null, "_id DESC", str);
    }

    public Cursor getUpdateListCursor(String whereClause, int limit) {
        String str = limit + BuildConfig.FLAVOR;
        return this.openHelper.getReadableDatabase().query(DBConstants.TABLE_UPDATE, null, whereClause, null, null, null, "_id DESC", str);
    }

    public UpdateModel getUpdateFromCursor(Cursor cursor) {
        boolean isNew = false;
        Long id = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBConstants.ID)));
        int type = cursor.getInt(cursor.getColumnIndex(DBConstants.UPDATE_COL_TYPE));
        System.out.println("Up Type "+type);
        String oldValue = cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_COL_OLD_VALUE));
        String newValue = cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_COL_NEW_VALUE));
        int userId = cursor.getInt(cursor.getColumnIndex(DBConstants.UPDATE_COL_USER_ID));
        if (!cursor.isNull(cursor.getColumnIndex(DBConstants.UPDATE_COL_IS_NEW)) && cursor.getLong(cursor.getColumnIndex(DBConstants.UPDATE_COL_IS_NEW)) > 0) {
            isNew = true;
        }
        return new UpdateModel(id, type, oldValue, newValue, userId, isNew, cursor.getString(cursor.getColumnIndex(DBConstants.UPDATE_COL_CHANGE_DATE)));
    }

    public Long insertOrUpdateAlarm(AlarmResponse alarm) {
        SQLiteDatabase writableDatabase = this.openHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            if (alarm.getId() != null) {
                contentValues.put(DBConstants.ID, alarm.getId());
            }
            contentValues.put(DBConstants.ALARM_COL_TITLE, alarm.getTitle());
            contentValues.put(DBConstants.ALARM_COL_MESSAGE, alarm.getMessage());
            contentValues.put(DBConstants.ALARM_COL_IMAGE_URL, alarm.getImageUrl());
            contentValues.put(DBConstants.ALARM_COL_POSITIVE_BTN_TEXT, alarm.getPositiveBtnText());
            contentValues.put(DBConstants.ALARM_COL_POSITIVE_BTN_ACTION, alarm.getPositiveBtnAction());
            contentValues.put(DBConstants.ALARM_COL_POSITIVE_BTN_URL, alarm.getPositiveBtnUrl());
            contentValues.put(DBConstants.ALARM_COL_NEGATIVE_BTN_TEXT, alarm.getNegativeBtnText());
            contentValues.put(DBConstants.ALARM_COL_NEGATIVE_BTN_ACTION, alarm.getNegativeBtnAction());
            contentValues.put(DBConstants.ALARM_COL_NEGATIVE_BTN_URL, alarm.getNegativeBtnUrl());
            contentValues.put(DBConstants.ALARM_COL_SHOW_COUNT, alarm.getShowCount());
            contentValues.put(DBConstants.ALARM_COL_EXIT_ON_DISMISS, Integer.valueOf(alarm.getExitOnDismiss().booleanValue() ? 1 : 0));
            contentValues.put(DBConstants.ALARM_COL_TARGET_NETWORK, alarm.getTargetNetwork());
            if (alarm.getDisplayCount() != null) {
                contentValues.put(DBConstants.ALARM_COL_DISPLAY_COUNT, alarm.getDisplayCount());
            }
            contentValues.put(DBConstants.ALARM_COL_TARGET_VERSION, alarm.getTargetVersion());
            Long valueOf;
            if (alarm.getId() == null || getAlarm(alarm.getId().longValue()) == null) {
                long result = writableDatabase.insertOrThrow(DBConstants.TABLE_ALARM, null, contentValues);
                writableDatabase.setTransactionSuccessful();
                valueOf = Long.valueOf(result);
                return valueOf;
            }
            writableDatabase.update(DBConstants.TABLE_ALARM, contentValues, "_id=" + alarm.getId().longValue(), null);
            writableDatabase.setTransactionSuccessful();
            valueOf = alarm.getId();
            writableDatabase.endTransaction();
            return valueOf;
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public AlarmResponse getAlarm(long id) {
        String whereClause = "_id=" + id;
        Cursor cursor = null;
        try {
            cursor = this.openHelper.getReadableDatabase().query(DBConstants.TABLE_ALARM, null, whereClause, null, null, null, DBConstants.ID);
            if (!cursor.moveToFirst()) {
                return null;
            }
            AlarmResponse alarmFromCursor = getAlarmFromCursor(cursor);
            if (cursor == null) {
                return alarmFromCursor;
            }
            cursor.close();
            return alarmFromCursor;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public AlarmResponse getLastAlarm(int versionCode) {
        String whereClause = "targetVersion = " + versionCode;
        Cursor cursor = null;
        try {
            cursor = this.openHelper.getReadableDatabase().query(DBConstants.TABLE_ALARM, null, whereClause, null, null, null, DBConstants.ID);
            if (!cursor.moveToLast()) {
                return null;
            }
            AlarmResponse alarmFromCursor = getAlarmFromCursor(cursor);
            if (cursor == null) {
                return alarmFromCursor;
            }
            cursor.close();
            return alarmFromCursor;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public AlarmResponse getAlarmFromCursor(Cursor cursor) {
        boolean exitOnDismiss;
        Long id = Long.valueOf(cursor.getLong(cursor.getColumnIndex(DBConstants.ID)));
        String title = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_TITLE));
        String message = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_MESSAGE));
        String imageUrl = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_IMAGE_URL));
        String positiveBtnText = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_POSITIVE_BTN_TEXT));
        String positiveBtnAction = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_POSITIVE_BTN_ACTION));
        String positiveBtnUrl = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_POSITIVE_BTN_URL));
        String negativeBtnText = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_NEGATIVE_BTN_TEXT));
        String negativeBtnAction = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_NEGATIVE_BTN_ACTION));
        String negativeBtnUrl = cursor.getString(cursor.getColumnIndex(DBConstants.ALARM_COL_NEGATIVE_BTN_URL));
        int showCount = cursor.getInt(cursor.getColumnIndex(DBConstants.ALARM_COL_SHOW_COUNT));
        if (cursor.isNull(cursor.getColumnIndex(DBConstants.ALARM_COL_EXIT_ON_DISMISS))) {
            exitOnDismiss = false;
        } else {
            exitOnDismiss = cursor.getLong(cursor.getColumnIndex(DBConstants.ALARM_COL_EXIT_ON_DISMISS)) > 0;
        }
        return new AlarmResponse(id, title, message, imageUrl, positiveBtnText, positiveBtnAction, positiveBtnUrl, negativeBtnText, negativeBtnAction, negativeBtnUrl, Integer.valueOf(showCount), Boolean.valueOf(exitOnDismiss), Integer.valueOf(cursor.getInt(cursor.getColumnIndex(DBConstants.ALARM_COL_TARGET_NETWORK))), Integer.valueOf(cursor.getInt(cursor.getColumnIndex(DBConstants.ALARM_COL_DISPLAY_COUNT))), Integer.valueOf(cursor.getInt(cursor.getColumnIndex(DBConstants.ALARM_COL_TARGET_VERSION))));
    }


    public void close() {
        this.openHelper.close();
    }


}
