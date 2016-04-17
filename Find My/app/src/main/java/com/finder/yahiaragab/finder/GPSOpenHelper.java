package com.finder.yahiaragab.finder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.finder.yahiaragab.finder.GPSData.GPSDataTableInfo;
import com.google.android.gms.maps.model.LatLng;

public class GPSOpenHelper extends SQLiteOpenHelper
{
    private static final String MARKER_TABLE_CREATE =
            "CREATE TABLE " + GPSDataTableInfo.MARKER_TABLE_NAME + " (" +
                    GPSDataTableInfo.MARKER_USER_ID + " NUMBER(10) PRIMARY KEY " +
                    GPSDataTableInfo.MARKER_LATITUDE + " REAL " +
                    GPSDataTableInfo.MARKER_LONGITUDE + " REAL " +
                    GPSDataTableInfo.MARKER_TIME + " TIME " +
                    ");";

    GPSOpenHelper(Context context) {
        super(context, GPSDataTableInfo.DATABASE_NAME, null, GPSDataTableInfo.DATABASE_VERSION);
        Log.d("GPSOpenHelper", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MARKER_TABLE_CREATE);
        Log.d("GPSOpenHelper", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertInfo(GPSOpenHelper goh, String userID, Location loc)
    {
        SQLiteDatabase SQDB = goh.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put(GPSDataTableInfo.MARKER_USER_ID, userID);
        cv.put(GPSDataTableInfo.MARKER_LATITUDE, loc.getLatitude() );
        cv.put(GPSDataTableInfo.MARKER_LONGITUDE, loc.getLongitude() );
        cv.put(GPSDataTableInfo.MARKER_TIME, loc.getTime());

        long ret = SQDB.insert(GPSDataTableInfo.MARKER_TABLE_NAME, null, cv);

        Log.d("GPSOpenHelper", "Onr row inserted");

    }
}
