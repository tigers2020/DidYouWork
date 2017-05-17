package com.androidnerdcolony.didyouwork.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidnerdcolony.didyouwork.data.DywContract.DywEntries;

/**
 * Created by pomkim on 5/14/17.
 */

public class DywDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Dyw_db";
    private final String PROJECT_TABLE_NAME = "Projects";
    private final String ENTRIES_TABLE_NAME = "Entries";
    private static final int DB_VERSION = 1;

    public DywDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROJECT_TABLE = "CREATE TABLE " + PROJECT_TABLE_NAME +
                DywEntries._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DywEntries.COLUMN_PROJECT_NAME + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_WAGE + " REAL, NOT NULL, " +
                DywEntries.COLUMN_PROJECT_LOCATION + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_CREATED_DATE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_PROJECT_TYPE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_PROJECT_TAGS + " TEXT NOT NULL, " +
                DywEntries.COLUMN_PROJECT_DEAD_LINE + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_TIME_ROUNDING + " INTEGER, " +
                DywEntries.COLUMN_PROJECT_DESCRIPTION + " TEXT" +
                ");";
        String CREATE_ENTRIES_TABLE = "CREATE TABLE " + ENTRIES_TABLE_NAME +
                DywEntries.COLUMN_ENTRIES_PROJECT_ID + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_ENTRIES_START_DATE + " INTEGER NOT NULL, " +
                DywEntries.COLUMN_ENTRIES_END_DATE + " INTEGER, " +
                DywEntries.COLUMN_ENTRIES_TAGS + " TEXT, " +
                DywEntries.COLUMN_ENTRIES_OVER_TIME + " TEXT, " +
                DywEntries.COLUMN_ENTRIES_BONUS + " REAL, " +
                DywEntries.COLUMN_ENTRIES_DESCRIPTION + " TEXT " +
                ");";
        db.execSQL(CREATE_PROJECT_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
