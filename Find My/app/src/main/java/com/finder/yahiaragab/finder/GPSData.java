package com.finder.yahiaragab.finder;

import android.provider.BaseColumns;

public final class GPSData
{
    // This class cannot be instantiated
    private GPSData() {}

    public static abstract class GPSDataTableInfo implements BaseColumns
    {
        // This class cannot be instantiated
        private GPSDataTableInfo() {}

        public static final String DATABASE_NAME = "TrackMeBack.db";
        public static final int DATABASE_VERSION = 5;

        public static final String MARKER_TABLE_NAME = "marker";

        public static final String MARKER_NAME = "name";
        public static final String MARKER_LONGITUDE = "longitude";
        public static final String MARKER_LATITUDE = "latitude";
        public static final String MARKER_TIME = "time";

    }

}