package com.androidnerdcolony.didyouwork.data;

import android.content.ContentResolver;
import android.provider.BaseColumns;

/**
 * Created by pomkim on 5/14/17.
 */

public class DywContract {
    private DywContract(){}

    public static final class DywEntries implements BaseColumns{
        public static final String CONTENT_AUTHORITY = "com.androidnerdcolony.didyouwork";
        public static final String PATH_PROJECT = "project";
        public static final String PATH_ENTRIES = "entries";
       public static final String TABLE_PROJECT = PATH_PROJECT;
        public static final String TABLE_ENTRIES = PATH_ENTRIES;

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROJECT;

        public static final String COLUMN_PROJECT_NAME = "project_name";
        public static final String COLUMN_PROJECT_WAGE = "wage";
        public static final String COLUMN_PROJECT_LOCATION = "location";
        public static final String COLUMN_PROJECT_CREATED_DATE = "create_date";
        public static final String COLUMN_PROJECT_TAGS = "tags";
        public static final String COLUMN_PROJECT_DEAD_LINE = "dead_line";
        public static final String COLUMN_PROJECT_TIME_ROUNDING = "time_rounding";
        public static final String COLUMN_PROJECT_TYPE = "project_type";
        public static final String COLUMN_PROJECT_DESCRIPTION = "description";

        public static final String COLUMN_ENTRIES_PROJECT_ID = "project_id";
        public static final String COLUMN_ENTRIES_START_DATE = "start_date";
        public static final String COLUMN_ENTRIES_END_DATE = "end_date";
        public static final String COLUMN_ENTRIES_TAGS = "tags";
        public static final String COLUMN_ENTRIES_OVER_TIME = "over_time";
        public static final String COLUMN_ENTRIES_BONUS = "bonus";
        public static final String COLUMN_ENTRIES_DESCRIPTION = "description";

    }
}
