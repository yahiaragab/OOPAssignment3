package com.finder.yahiaragab.finder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class GPSOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "gpsdata.db";
    private static final int DATABASE_VERSION = 1;

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

    DictionaryOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DICTIONARY_TABLE_CREATE);
    }
}
