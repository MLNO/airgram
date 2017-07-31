package org.telegram.ui.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.telegram.messenger.ApplicationLoader;

public class MobogramDBHelper extends SQLiteOpenHelper {
    public MobogramDBHelper(Context context) {
        super(context, MoboUtils.getAppName(context) + ".db", null, MoboUtils.getAppVersionCode(context));
    }

    public void onCreate(SQLiteDatabase db) {
        createUpdateTable(db);
        createSettingTable(db);
        createAlarmTable(db);
        createFavoriteTable(db);
    }

    private void createUpdateTable(SQLiteDatabase db) {
        db.execSQL("create table tbl_update ( _id integer primary key autoincrement, type integer,old_value text,new_value text,user_id integer,is_new integer,change_date integer default (strftime('%s','now') * 1000))");
    }

    private void createSettingTable(SQLiteDatabase db) {
        db.execSQL("create table tbl_setting ( _id integer primary key autoincrement, key text, value text)");
        db.execSQL("INSERT INTO tbl_setting VALUES (1,'notifyChanges','true')");
        db.execSQL("INSERT INTO tbl_setting VALUES (2,'notifyNameChanges','true')");
        db.execSQL("INSERT INTO tbl_setting VALUES (3,'notifyStatusChanges','true')");
        db.execSQL("INSERT INTO tbl_setting VALUES (4,'notifyPhotoChanges','true')");
        db.execSQL("INSERT INTO tbl_setting VALUES (5,'notifyPhoneChanges','true')");
        System.out.println("Create Table Done");
    }

    private void createAlarmTable(SQLiteDatabase db) {
        db.execSQL("create table tbl_alarm ( _id integer primary key autoincrement, title text,message text,imageUrl text,positiveBtnText text,positiveBtnAction text,positiveBtnUrl text,negativeBtnText text,negativeBtnAction text,negativeBtnUrl text,showCount integer,exitOnDismiss integer,targetNetwork integer,displayCount integer,targetVersion integer)");
    }

    private void createFavoriteTable(SQLiteDatabase db) {
        db.execSQL("create table tbl_favorite ( _id integer primary key autoincrement, chatID integer)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int currentVersion = oldVersion + 1;
        if (currentVersion == 1) {
            currentVersion++;
        }
        if (currentVersion <= 65420) {
            currentVersion = 65421;
        }
        if (currentVersion == 65421) {
            currentVersion++;
            createAlarmTable(db);
        }
        if (currentVersion == 65422) {
            currentVersion++;
        }
        if (currentVersion == 65423) {
            currentVersion++;
        }
        if (currentVersion == 65424) {
            currentVersion++;
            db.execSQL("drop table tbl_alarm");
            createAlarmTable(db);
            createFavoriteTable(db);
        }
        if (currentVersion <= 68528) {
            currentVersion = 68529;
        }
        if (currentVersion == 68529) {
            currentVersion++;
            SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
            if (preferences.getInt("default_tab", 0) == 2) {
                preferences.edit().putInt("default_tab", 7).commit();
            }
        }
    }
}
