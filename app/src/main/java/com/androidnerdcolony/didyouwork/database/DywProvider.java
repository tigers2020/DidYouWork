package com.androidnerdcolony.didyouwork.database;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.androidnerdcolony.didyouwork.R;

import static com.androidnerdcolony.didyouwork.database.DywContract.CONTENT_AUTHORITY;
import static com.androidnerdcolony.didyouwork.database.DywContract.PATH_ENTRIES;
import static com.androidnerdcolony.didyouwork.database.DywContract.PATH_PROJECT;
import static com.androidnerdcolony.didyouwork.database.DywContract.TABLE_ENTRIES;
import static com.androidnerdcolony.didyouwork.database.DywContract.TABLE_PROJECT;

/**
 * Created by pomkim on 5/14/17.
 */

public class DywProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PROJECT = 100;
    private static final int PROJECT_ID = 101;
    private static final int ENTRIES = 200;
    private static final int ENTRIES_ID = 201;

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PROJECT, PROJECT);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PROJECT + "/#", PROJECT_ID);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ENTRIES, ENTRIES);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_ENTRIES + "/#", ENTRIES_ID);
    }

    private DywDbHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DywDbHelper(getContext());
        return false;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PROJECT:
                return insertProject(uri, values);
            case ENTRIES:
                return  insertEntries(uri, values);
            default:
                throw new IllegalArgumentException("Failed to insert ProjectDataStructure or Entries with " + match);
        }
    }

    private Uri insertEntries(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(TABLE_ENTRIES, null, values);
        if (id != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertProject(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(TABLE_PROJECT, null, values);
        if (id != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ContentUris.withAppendedId(uri, id);
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match) {
            case PROJECT:
                cursor = db.query(TABLE_PROJECT, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PROJECT_ID:
                selection = DywContract.DywEntries._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_PROJECT, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ENTRIES:
                cursor = db.query(TABLE_ENTRIES, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case ENTRIES_ID:
                selection = DywContract.DywEntries._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(TABLE_ENTRIES, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException(getContext().getString(R.string.cannot_query_unknown_uri) + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        int delRow;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (match) {
            case PROJECT:
                delRow = db.delete(DywContract.TABLE_PROJECT, selection, selectionArgs);
                break;
            case PROJECT_ID:
                selection = DywContract.DywEntries._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                delRow = db.delete(TABLE_PROJECT, selection, selectionArgs);
                break;
            case ENTRIES:
                delRow = db.delete(TABLE_ENTRIES, selection, selectionArgs);
                break;
            case ENTRIES_ID:
                selection = DywContract.DywEntries._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                delRow = db.delete(TABLE_ENTRIES, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed to delete ProjectDataStructure or Entries with " + match);

        }
        if (delRow != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return delRow;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);

        switch (match) {
            case PROJECT_ID:
                return updateProject(uri, values, selection, selectionArgs);
            case ENTRIES_ID:
                return updateEntry(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Failed to Update ProjectDataStructure or Entries with " + uri);
        }
    }

    private int updateEntry(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int updateRow = db.update(TABLE_ENTRIES, values, selection, selectionArgs);

        if (updateRow != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateRow;
    }

    private int updateProject(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateRow = db.update(TABLE_PROJECT, values, selection, selectionArgs);

        if (updateRow != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateRow;
    }
}
