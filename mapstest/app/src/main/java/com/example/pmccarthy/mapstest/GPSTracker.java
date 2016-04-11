package com.example.pmccarthy.mapstest;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

public class GPSTracker extends Service implements LocationListener
{
    private final Context context;

    //variables for class
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;

    double latitude;
    double longitude;

    // update variables
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;


    protected LocationManager locationManager;

    public GPSTracker(Context context)
    {
        this.context = context;
        getLocation();
    }


    public Location getLocation()
    {
        try
        {

            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            //tests if gps on phone is on
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // checks if phone has network
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return location;
    }

}
