package com.finder.yahiaragab.finder;

import android.net.Uri;
import android.provider.BaseColumns;

public final class GPSData {
    public static final String AUTHORITY = "com.finder.yahiaragab.finder";

    // This class cannot be instantiated
    private GPSData() {}

    /**
     * GPS data table
     */
    public static final class GPSPoint implements BaseColumns {
        // This class cannot be instantiated
        private GPSPoint() {}

        public static final String MARKER_TABLE_NAME = "marker";
        public static final String LONGITUDE = "longitude";
        public static final String LATITUDE = "latitude";
        public static final String TIME = "time";

        private static final String MARKER_TABLE_CREATE =
                "CREATE TABLE " + MARKER_TABLE_NAME + " (" +
                        LATITUDE + " REAL " +
                        LONGITUDE + " REAL " +
                        TIME + " TIME " +
                        ");";


//        /**
//         * The content:// style URL for this table
//         */
//        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/gpspoint");
//
//        /**
//         * The MIME type of {@link #CONTENT_URI} providing a track (list of points).
//         */
//        public static final String CONTENT_TYPE = "mime/text";
//
//        /**
//         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single point.
//         */
//        public static final String CONTENT_ITEM_TYPE = "";
//
//        /**
//         * The default sort order for this table
//         */
//        public static final String DEFAULT_SORT_ORDER = "modified DESC";


    }

}
