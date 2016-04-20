package com.finder.yahiaragab.finder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.finder.yahiaragab.finder.GPSData.GPSDataTableInfo;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GPSOpenHelper extends SQLiteOpenHelper
{
    private static final String MARKER_TABLE_CREATE =
            "CREATE TABLE " + GPSDataTableInfo.MARKER_TABLE_NAME + " (" +
                    GPSDataTableInfo.MARKER_NAME + " TEXT, " +
                    GPSDataTableInfo.MARKER_LATITUDE + " REAL, " +
                    GPSDataTableInfo.MARKER_LONGITUDE + " REAL, " +
                    GPSDataTableInfo.MARKER_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
                    ");";

    GPSOpenHelper(Context context)
    {
        super(context, GPSDataTableInfo.DATABASE_NAME, null, GPSDataTableInfo.DATABASE_VERSION);
        Log.d("GPSOpenHelper", "Database Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MARKER_TABLE_CREATE);
        Log.d("GPSOpenHelper", "Table Created");
        System.out.print("Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertLatlng(GPSOpenHelper db, LatLng latLng, String markerName)
    {
        try {
            SQLiteDatabase SQDB = db.getWritableDatabase();
            ContentValues cv = new ContentValues();

            String dateTime = getDateTime();

            cv.put(GPSDataTableInfo.MARKER_NAME, markerName);
            cv.put(GPSDataTableInfo.MARKER_LATITUDE, latLng.latitude);
            cv.put(GPSDataTableInfo.MARKER_LONGITUDE, latLng.longitude);
            cv.put(GPSDataTableInfo.MARKER_TIME, dateTime);


            long ret = SQDB.insert(GPSDataTableInfo.MARKER_TABLE_NAME, null, cv);

            Log.d("GPSOpenHelper", "One row inserted");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    //retrieving info
    public Cursor getRecentMarkers(GPSOpenHelper db, String limit)
    {
        SQLiteDatabase sqdb = db.getReadableDatabase();
        String currentDate = getDateTime();
        String[] columns =
                {
                        GPSDataTableInfo.MARKER_NAME,
                        GPSDataTableInfo.MARKER_LATITUDE,
                        GPSDataTableInfo.MARKER_LONGITUDE,
                        GPSDataTableInfo.MARKER_TIME
                };
        Cursor cr = sqdb.query(GPSDataTableInfo.MARKER_TABLE_NAME, columns,
                  GPSDataTableInfo.MARKER_TIME + " < '" + currentDate + "'", null, null, null, null, limit);
        return cr;
    }

    //retrieving info
    public Cursor deleteOldMarkers(GPSOpenHelper db)
    {
        SQLiteDatabase sqdb = db.getReadableDatabase();
        String currentDate = getDateTime();
        String[] columns =
                {
                        GPSDataTableInfo.MARKER_NAME,
                        GPSDataTableInfo.MARKER_LATITUDE,
                        GPSDataTableInfo.MARKER_LONGITUDE,
                        GPSDataTableInfo.MARKER_TIME
                };
//        Cursor cr = sqdb.delete(GPSDataTableInfo.MARKER_TABLE_NAME, columns,
//                GPSDataTableInfo.MARKER_TIME + " < '" + currentDate + "'", null, null, null, null, null);
        sqdb.execSQL("delete from " +GPSDataTableInfo.MARKER_TABLE_NAME +
                "Where time  > " + (currentDate - 0) + ";", new String[]{});

        return cr;
    }



}
