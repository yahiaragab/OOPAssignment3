package com.finder.yahiaragab.finder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.*;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.app.Dialog;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity
        implements OnMapClickListener, OnMapLongClickListener, OnMapReadyCallback, OnTouchListener,
        OnMarkerClickListener, OnInfoWindowClickListener, OnInfoWindowLongClickListener,
        OnMarkerDragListener, LocationListener {

    GPSOpenHelper db;
    private GoogleMap mMap;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    private GoogleApiClient client;
    GPSTracker gps;
    LatLng userLatLng;
    Polyline line;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // HERES THE BUTTON CODE
        Button button = new Button(this);
        button.setText("Drop Pin Here");
        addContentView(button, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {


            // THIS IS WHERE THE DRAW THE LINE FUNCTION SHOULD BE PUT
            @Override
            public void onClick(View v)
            {
                addMarker(userLatLng);

            }

        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        gps = new GPSTracker(MapsActivity.this);

        userLatLng = new LatLng(gps.location.getLatitude(), gps.location.getLongitude());

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // LatLng Ststephensgreen1 = new LatLng(56.338340, -15.259376);
        float zoomlevel = 15;
        //sets where camera starts and the zoom level of the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomlevel));

        // user location showed
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);



        //new zoom options
//        UiSettings.setZoomControlsEnabled(true);

//        if (distanceInMeters < 5)
//        {
//            Toast.makeText(this, "You are 5 meters away",
//                    Toast.LENGTH_SHORT).show();
//
//        }
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("NULL"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.finder.yahiaragab.finder/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("NULL"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.finder.yahiaragab.finder/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public boolean onTouch(View v, MotionEvent me)
    {
//        float xx = me.getX();
//        float yy = me.getY();
//
//        switch (me.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//        }
        return true;
    }


    @Override
    public void onMapClick(LatLng latLng)
    {
        if (line != null) {
            line.remove();
        }
    }



    @Override
    public void onMapLongClick(final LatLng latLng)
    {
//        Location loc = new Location("Marker");
//        loc.setLatitude(latLng.latitude);
//        loc.setLongitude(latLng.longitude);

        addMarker(latLng);

//        Log.e("GPSDataContentProvider", loc.toString());
//
//        ContentValues values = new ContentValues();
//
//        values.put(GPSData.GPSPoint.LONGITUDE, loc.getLongitude());
//        values.put(GPSData.GPSPoint.LATITUDE, loc.getLatitude());
//        values.put(GPSData.GPSPoint.TIME, loc.getTime());
//        getContentResolver().insert(GPSDataContentProvider.CONTENT_URI, values);
//        if (values != null)
//        {
//            System.out.println("YEEEEEEEEEES");
//        }
    }

    private String markerName;
    int pinNum = 1;
    double distanceInMeters= 0;

    public void addMarker(final LatLng latLng)
    {
        markerName = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New pin name:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markerName = input.getText().toString();

                if (markerName.equals("")) {
                    markerName = "Pin " + pinNum;
                    pinNum++;
                }

                markers.add(mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title(markerName)
                                .snippet("Tap and hold to delete pin")
                                .draggable(true)));
                markerName = "";
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();



    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        //HOW TO FIND OUT WHICH MARKER LINE IS CONNECTED TO

        DecimalFormat df = new DecimalFormat("#0.0");

        if (line != null)
        {
            line.remove();
        }

        marker.showInfoWindow();

        userLatLng = new LatLng(gps.location.getLatitude(), gps.location.getLongitude());

        distanceInMeters = SphericalUtil.computeDistanceBetween(userLatLng, marker.getPosition());
        System.out.println("Distance between two points is " + distanceInMeters);

        drawLine(userLatLng, marker);

        Toast.makeText(this, "Pin: " + df.format(distanceInMeters) + "m away.",
                Toast.LENGTH_SHORT).show();

        return true;
    }

    Marker destMarker;

    public void drawLine(LatLng latLng, Marker marker)
    {
        destMarker = null;
        line = mMap.addPolyline(new PolylineOptions()
                .add(latLng).add(markers.get(markers.indexOf(marker)).getPosition())
                .color(Color.BLUE).width(15));
        destMarker = marker;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        //get directions to this marker
    }


    @Override
    public void onInfoWindowLongClick(Marker marker)
    {
        //if the user deletes the pin the line is on, delete the line

        marker.remove();
        markers.remove(markers.indexOf(marker));
//        marker.setTitle();
        Toast.makeText(this, "Pin deleted",
                Toast.LENGTH_SHORT).show();

        if ( markers.indexOf(marker) == markers.indexOf(destMarker) )
        {
            line.remove();
        }


    }

    //Thought we need to sort, but it's sorted ascendingly already
//
//    public void sortMarkers(Marker newMrkr)
//    {
//        int j=0;                     // the number of items sorted so far
//                        // the item to be inserted
//        int i= 0;
//
//        for (j = 1; j < markers.size(); j++)    // Start with 1 (not 0)
//        {
//            newMrkr = markers.get(j);
//            // Smaller values are moving up
//            for(i = j - 1;
//                (i >= 0) && ( markers.get(i).getId().compareTo( newMrkr.getId() ) ) < 10 ;
//                i--)
//            {
//                markers.set(i + 1, markers.get(i));
//            }
//            markers.set(i+1, newMrkr);    // Put the key in its proper location
//        }
//
//        System.out.print("-------Sorted: ");
//        for (j = 0; j < markers.size(); j++)    // Start with 1 (not 0)
//        {
//
//            System.out.print(markers.get(j).getId() + ", ");
//        }
//        System.out.print("\n");
//
//    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        gps.location = location;
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        line.remove();
        drawLine(ll, destMarker);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
