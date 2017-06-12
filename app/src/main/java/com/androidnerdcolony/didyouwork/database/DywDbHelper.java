package com.androidnerdcolony.didyouwork.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidnerdcolony.didyouwork.database.DywContract.DywEntries;

import timber.log.Timber;

/**
 * Database Helper
 */

class DywDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Dyw_db";
    private static final int DB_VERSION = 1;

    DywDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECT_TABLE = "CREATE TABLE " + DywContract.TABLE_PROJECT + " (" +
                DywEntries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DywEntries.COLUMN_PROJECT_NAME + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_WAGE + " REAL NOT NULL, " +
                DywEntries.COLUMN_PROJECT_LOCATION + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_CREATED_DATE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_PROJECT_TYPE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_PROJECT_WORK_TIME + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_LAST_ACTIVITY + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_TAGS + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_DEAD_LINE + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_TIME_ROUNDING + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_DESCRIPTION + " TEXT" +
                ");";
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + DywContract.TABLE_ENTRIES + " (" +
                DywEntries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DywEntries.COLUMN_ENTRIES_PROJECT_ID + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_ENTRIES_START_DATE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_ENTRIES_END_DATE + " INTEGER, " +
                DywEntries.COLUMN_ENTRIES_TAGS + " TEXT, " +
                DywEntries.COLUMN_ENTRIES_OVER_TIME + " TEXT, " +
                DywEntries.COLUMN_ENTRIES_BONUS + " REAL, " +
                DywEntries.COLUMN_ENTRIES_ACTIVE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_ENTRIES_DESCRIPTION + " TEXT " +
                ");";
        Timber.d(CREATE_PROJECT_TABLE);
        Timber.d(CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_PROJECT_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
