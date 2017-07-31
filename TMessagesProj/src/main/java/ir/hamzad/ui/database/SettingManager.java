package org.telegram.ui.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;


public class SettingManager {
    private MobogramDBHelper openHelper;

    public SettingManager() {
        this.openHelper = ApplicationLoader.getOpenHelper();
    }

    public String getStringValue(String key) {
        return getValue(key);
    }

    public int getIntValue(String key) {
        String value = getValue(key);
        if (value != null) {
            return Integer.parseInt(value);
        }
        return 0;
    }

    public boolean getBooleanValue(String key) {
        String value = getValue(key);
        if (value != null) {
            return Boolean.parseBoolean(value);
        }
        return false;
    }

    public void setIntValue(String key, int value) {
        setValue(key, value + BuildConfig.FLAVOR);
    }

    public void setBooleanValue(String key, boolean value) {
        setValue(key, value + BuildConfig.FLAVOR);
    }

    public void setStringValue(String key, String value) {
        setValue(key, value);
    }

    private void setValue(String key, String value) {
        SQLiteDatabase writableDatabase = this.openHelper.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBConstants.SETTING_COL_KEY, key);
            contentValues.put(DBConstants.SETTING_COL_VALUE, value);
            if (getValue(key) == null) {
                writableDatabase.insertOrThrow(DBConstants.TABLE_SETTING, null, contentValues);
            } else {
                writableDatabase.update(DBConstants.TABLE_SETTING, contentValues, "key='" + key + "'", null);
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    private String getValue(String key) {
        Cursor cursor = null;
        try {
            cursor = this.openHelper.getReadableDatabase().query(DBConstants.TABLE_SETTING, null, "key= '" + key + "'", null, null, null, null);
            if (!cursor.moveToNext()) {
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndex(DBConstants.SETTING_COL_VALUE));
            if (cursor == null) {
                return string;
            }
            cursor.close();
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
