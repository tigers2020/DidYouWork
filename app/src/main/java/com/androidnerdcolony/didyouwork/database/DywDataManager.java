package com.androidnerdcolony.didyouwork.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateUtils;

import com.androidnerdcolony.didyouwork.data.EntryDataStructure;
import com.androidnerdcolony.didyouwork.data.ProjectDataStructure;

import java.util.Date;

import timber.log.Timber;

/**
 * Created by pomkim on 6/12/17.
 */

public class DywDataManager {

    public static ContentValues getDefaultProjectValue() {
        ContentValues projectValue = new ContentValues();
        Date date = new Date();
        long timeLong = date.getTime();
        long duration = DateUtils.DAY_IN_MILLIS;
        long workTime = DateUtils.HOUR_IN_MILLIS * 8;
        double wage = 0;
        String projectName = "NO NAME";
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_CREATED_DATE, timeLong);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_NAME, projectName);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_DEAD_LINE, timeLong);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_DESCRIPTION, "");
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_DURATION, duration);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_LAST_ACTIVITY, timeLong);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_TYPE, 0);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_WORK_TIME, workTime);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_TAGS, "");
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_WAGE, wage);
        projectValue.put(DywContract.DywEntries.COLUMN_PROJECT_TIME_ROUNDING, 0);

        return projectValue;
    }

    public static ContentValues getDefaultEntryValue() {
        ContentValues entryValue = new ContentValues();
        Date date = new Date();
        long timeLong = date.getTime();
        long overTime = DateUtils.HOUR_IN_MILLIS * 8;
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_ACTIVE, 0);
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_BONUS, "");
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_DESCRIPTION, "");
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_END_DATE, timeLong);
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_OVER_TIME, overTime);
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID, -1);
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_START_DATE, timeLong);
        entryValue.put(DywContract.DywEntries.COLUMN_ENTRIES_TAGS, "");

        return entryValue;
    }
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
