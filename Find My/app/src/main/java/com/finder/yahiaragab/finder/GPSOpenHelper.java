package com.finder.yahiaragab.finder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
//                    GPSDataTableInfo.MARKER_USER_ID + " NUMBER(10) PRIMARY KEY " +
                    GPSDataTableInfo.MARKER_LATITUDE + " REAL, " +
                    GPSDataTableInfo.MARKER_LONGITUDE + " REAL " +
//                    GPSDataTableInfo.MARKER_TIME + " TIME " +
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

    public void insertLocation(GPSOpenHelper goh, Location loc)
    {
        SQLiteDatabase SQDB = goh.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GPSDataTableInfo.MARKER_LATITUDE, loc.getLatitude() );
        cv.put(GPSDataTableInfo.MARKER_LONGITUDE, loc.getLongitude() );
        cv.put(GPSDataTableInfo.MARKER_TIME, loc.getTime());

        long ret = SQDB.insert(GPSDataTableInfo.MARKER_TABLE_NAME, null, cv);

        Log.d("GPSOpenHelper", "Onr row inserted");

    }

    public void insertLatlng(GPSOpenHelper db, LatLng latLng)
    {
//        boolean res = false;
//        try {
            SQLiteDatabase SQDB = db.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(GPSDataTableInfo.MARKER_LATITUDE, latLng.latitude);
            cv.put(GPSDataTableInfo.MARKER_LONGITUDE, latLng.longitude);

            long ret = SQDB.insert(GPSDataTableInfo.MARKER_TABLE_NAME, null, cv);

            if (ret > -1)
            {
                System.out.println("yoyoyo");
            }
            Log.d("GPSOpenHelper", "One row inserted");
//            res = true;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            res = false;
//        }
//
//        if (res)
//        {
//            System.out.println("hunda db write success");
//        }
//        else
//        {
//            System.out.println("hunda db write fail");
//        }

    }


    //retrieving info
    public Cursor getLatlng(GPSOpenHelper db)
    {
        SQLiteDatabase sqdb = db.getReadableDatabase();
        String[] columns =
                {
                        GPSDataTableInfo.MARKER_LATITUDE,
                        GPSDataTableInfo.MARKER_LONGITUDE
                };
        Cursor cr = sqdb.query(GPSDataTableInfo.MARKER_TABLE_NAME, columns,
                null, null, null, null, null);
        return cr;
    }



}
