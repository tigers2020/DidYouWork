package com.androidnerdcolony.didyouwork.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.androidnerdcolony.didyouwork.data.EntryDataStructure;
import com.androidnerdcolony.didyouwork.data.ProjectDataStructure;

import timber.log.Timber;

/**
 * Created by pomkim on 6/12/17.
 */

public class DywDataManager {

    public static ProjectDataStructure ConvertToProjectData(Cursor cursor){

        ProjectDataStructure dataStructure = new ProjectDataStructure();

        if (cursor.moveToFirst()){

            dataStructure.setId(cursor.getLong(DywContract.DywProjection.INDEX_PROJECT_ID));
            dataStructure.setProject_name(cursor.getString(DywContract.DywProjection.INDEX_PROJECT_NAME));
            dataStructure.setCreate_date(cursor.getLong(DywContract.DywProjection.INDEX_PROJECT_CREATED_DATE));
            dataStructure.setWage(cursor.getDouble(DywContract.DywProjection.INDEX_PROJECT_WAGE));
            dataStructure.setLocation(cursor.getString(DywContract.DywProjection.INDEX_PROJECT_LOCATION));
            dataStructure.setTags(cursor.getString(DywContract.DywProjection.INDEX_PROJECT_TAGS));
            dataStructure.setDead_line(cursor.getLong(DywContract.DywProjection.INDEX_PROJECT_DEAD_LINE));
            dataStructure.setTime_rounding(cursor.getInt(DywContract.DywProjection.INDEX_PROJECT_TIME_ROUNDING));
            dataStructure.setProject_type(cursor.getInt(DywContract.DywProjection.INDEX_PROJECT_TYPE));
            dataStructure.setLast_activity(cursor.getLong(DywContract.DywProjection.INDEX_PROJECT_LAST_ACTIVITY));
            dataStructure.setDescription(cursor.getString(DywContract.DywProjection.INDEX_PROJECT_DESCRIPTION));
            return dataStructure;}
        else {
            return null;
        }

    }
    public static EntryDataStructure ConvertToEntryData(Cursor cursor, int position){
        EntryDataStructure dataStructure = new EntryDataStructure();

        if (cursor.moveToPosition(position)) {
            dataStructure.set_id(DywContract.DywProjection.INDEX_ENTRIES_ID);
            dataStructure.setProject_id(cursor.getLong(DywContract.DywProjection.INDEX_ENTRIES_PROJECT_ID));
            dataStructure.setStart_date(cursor.getLong(DywContract.DywProjection.INDEX_ENTICES_START_DATE));
            dataStructure.setEnd_date(cursor.getLong(DywContract.DywProjection.INDEX_ENTRIES_END_DATE));
            dataStructure.setTags(cursor.getString(DywContract.DywProjection.INDEX_ENTRIES_TAGS));
            dataStructure.setBonus(cursor.getDouble(DywContract.DywProjection.INDEX_ENTRIES_BOUNUS));
            dataStructure.setActive(cursor.getInt(DywContract.DywProjection.INDEX_ENTRIES_ACTIVE));
            dataStructure.setDescription(cursor.getString(DywContract.DywProjection.INDEX_ENTRIES_DESCRIPTION));

            return dataStructure;
        }
        else {
            return null;
        }
    }
    public static boolean insertProjectData(Context context, ContentValues projectValues){
        if (checkProjectValue(projectValues)){
            Uri uri = context.getContentResolver().insert(DywContract.DywEntries.CONTENT_PROJECT_URI, projectValues);

            long id = Long.getLong(uri.getLastPathSegment());

            if (id != -1){
                Timber.d("Project Inserted: " + id);
                return true;
            }
        }
        return false;
    }
    public static boolean insertEntries(Context context, ContentValues entriesValues){
        if (checkEntryValues(entriesValues)){
            Uri uri = context.getContentResolver().insert(DywContract.DywEntries.CONTENT_ENTRIES_URI, entriesValues);

            long entriyId = ContentUris.parseId(uri);

            if (entriyId != -1){
                Timber.d("Entries Inserted: " + entriyId);
                return true;
            }else {
                return false;
            }

        }else {
            return false;
        }
    }
    public static boolean updateProject(Context context, Uri uri, ContentValues projectValues, String where, String[] whereArgs){
        if (checkProjectValue(projectValues)){
            context.getContentResolver().update(uri, projectValues, where, whereArgs);

        }else {
            return false;
        }
        return false;
    }

    public static boolean updateEntries(Context context, Uri uri, ContentValues entryValues, String wher, String[] whereArgs){

        if (checkEntryValues(entryValues)){
            context.getContentResolver().update(uri, entryValues, wher, whereArgs);
        }else {
            return false;
        }

        return false;
    }

    private static boolean checkEntryValues(ContentValues entriesValues) {

        return false;
    }

    private static boolean checkProjectValue(ContentValues projectValues) {

        return false;
    }
}
