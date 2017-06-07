package com.androidnerdcolony.didyouwork.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pomkim on 5/14/17.
 */

public class DywContract {
    static final String CONTENT_AUTHORITY = "com.androidnerdcolony.didyouwork";
    static final String PATH_PROJECT = "project";
    static final String PATH_ENTRIES = "entries";
    static final String TABLE_PROJECT = PATH_PROJECT;
    static final String TABLE_ENTRIES = PATH_ENTRIES;

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    private DywContract() {
    }

    public static final class DywEntries implements BaseColumns {

        public static final Uri CONTENT_PROJECT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROJECT).build();
        public static final Uri CONTENT_ENTRIES_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ENTRIES).build();

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;

        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_PROJECT_WAGE = "wage";
        public static final String COLUMN_PROJECT_LOCATION = "location";
        public static final String COLUMN_PROJECT_CREATED_DATE = "create_date";
        public static final String COLUMN_PROJECT_TAGS = "tags";
        public static final String COLUMN_PROJECT_DEAD_LINE = "dead_line";
        public static final String COLUMN_PROJECT_WORK_TIME = "work_time";
        public static final String COLUMN_PROJECT_TIME_ROUNDING = "time_rounding";
        public static final String COLUMN_PROJECT_TYPE = "project_type";
        public static final String COLUMN_PROJECT_LAST_ACTIVITY = "last_activity";
        public static final String COLUMN_PROJECT_DESCRIPTION = "description";

        public static final String COLUMN_ENTRIES_PROJECT_ID = "project_id";
        public static final String COLUMN_ENTRIES_START_DATE = "start_date";
        public static final String COLUMN_ENTRIES_END_DATE = "end_date";
        public static final String COLUMN_ENTRIES_TAGS = "tags";
        public static final String COLUMN_ENTRIES_OVER_TIME = "over_time";
        public static final String COLUMN_ENTRIES_BONUS = "bonus";
        public static final String COLUMN_ENTRIES_ACTIVE = "active";
        public static final String COLUMN_ENTRIES_DESCRIPTION = "description";

    }

    public static final class DywProjection {

        public static final String[] PROJECT_PROJECTION = new String[]{
                DywEntries._ID,
                DywEntries.COLUMN_PROJECT_NAME,
                DywEntries.COLUMN_PROJECT_CREATED_DATE,
                DywEntries.COLUMN_PROJECT_WAGE,
                DywEntries.COLUMN_PROJECT_LOCATION,
                DywEntries.COLUMN_PROJECT_TAGS,
                DywEntries.COLUMN_PROJECT_DEAD_LINE,
                DywEntries.COLUMN_PROJECT_WORK_TIME,
                DywEntries.COLUMN_PROJECT_TIME_ROUNDING,
                DywEntries.COLUMN_PROJECT_TYPE,
                DywEntries.COLUMN_PROJECT_LAST_ACTIVITY,
                DywEntries.COLUMN_PROJECT_DESCRIPTION
        };
        public static final int INDEX_PROJECT_ID = 0;
        public static final int INDEX_PROJECT_NAME = 1;
        public static final int INDEX_PROJECT_CREATED_DATE = 2;
        public static final int INDEX_PROJECT_WAGE = 3;
        public static final int INDEX_PROJECT_LOCATION = 4;
        public static final int INDEX_PROJECT_TAGS = 5;
        public static final int INDEX_PROJECT_DEAD_LINE = 6;
        public static final int INDEX_PROJECT_WORK_TIME = 7;
        public static final int INDEX_PROJECT_TIME_ROUNDING = 8;
        public static final int INDEX_PROJECT_TYPE = 9;
        public static final int INDEX_PROJECT_LAST_ACTIVITY = 10;
        public static final int INDEX_PROJECT_DESCRIPTION = 11;


        public static final String[] ENTRIES_PROJECTION = new String[]{
                DywEntries._ID,
                DywContract.DywEntries.COLUMN_ENTRIES_PROJECT_ID,
                DywContract.DywEntries.COLUMN_ENTRIES_START_DATE,
                DywContract.DywEntries.COLUMN_ENTRIES_END_DATE,
                DywContract.DywEntries.COLUMN_ENTRIES_TAGS,
                DywContract.DywEntries.COLUMN_ENTRIES_BONUS,
                DywEntries.COLUMN_ENTRIES_ACTIVE,
                DywContract.DywEntries.COLUMN_ENTRIES_DESCRIPTION

        };
        public static final int INDEX_ENTRIES_ID = 0;
        public static final int INDEX_ENTRIES_PROJECT_ID = 1;
        public static final int INDEX_ENTICES_START_DATE = 2;
        public static final int INDEX_ENTRIES_END_DATE = 3;
        public static final int INDEX_ENTRIES_TAGS = 4;
        public static final int INDEX_ENTRIES_BOUNUS = 5;
        public static final int INDEX_ENTRIES_ACTIVE = 6;
        public static final int INDEX_ENTRIES_DESCRIPTION = 7;
    }
}
