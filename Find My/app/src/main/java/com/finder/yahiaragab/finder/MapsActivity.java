package com.finder.yahiaragab.finder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.List;
import java.util.Random;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import android.app.Dialog;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.finder.yahiaragab.finder.GPSOpenHelper;
import com.finder.yahiaragab.finder.GPSData.*;

public class MapsActivity extends FragmentActivity
        implements OnMapClickListener, OnMapLongClickListener, OnMapReadyCallback, OnTouchListener,
        OnMarkerClickListener, OnInfoWindowClickListener, OnInfoWindowLongClickListener
        {

    GPSOpenHelper db;
    private static GoogleMap mMap;
    private static ArrayList<Marker> markers = new ArrayList<Marker>();
    private GoogleApiClient client;
    GPSTracker gps;
    public static LatLng userLatLng;
    public static Polyline line;
    Context ctx = this;
    float zoomlevel = 15;

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

        //Used these to find the height and width of the screen man
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float width = metrics.widthPixels;
        final float height = metrics.heightPixels;



        // HERES THE BUTTON CODE
        Button button = new Button(this);
        button.setText("Drop pin");
        button.setY(height - 200);
        addContentView(button, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {


            // THIS IS WHERE THE DRAW THE LINE FUNCTION SHOULD BE PUT
            @Override
            public void onClick(View v1) {
                addMarker(userLatLng);

            }

        });
        Button recent_places = new Button(this);
        recent_places.setText("Clear Pins");
        recent_places.setX((width-400)/2);
        addContentView(recent_places, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));

        recent_places.setOnClickListener(new View.OnClickListener() {


            // THIS IS WHERE THE DRAW THE LINE FUNCTION SHOULD BE PUT
            @Override
            public void onClick(View v) {
//                System.out.println("test");
                mMap.clear();
                markers.clear();
            }

        });

        Button clear_pins = new Button(this);
        clear_pins.setText("Recent Pins");
        clear_pins.setX(width-500);
        clear_pins.setY(height - 200);
        addContentView(clear_pins, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT));

        clear_pins.setOnClickListener(new View.OnClickListener() {

            String limit;
            // THIS IS WHERE THE DRAW THE LINE FUNCTION SHOULD BE PUT
            @Override
            public void onClick(View view)
            {
                limit = "";

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Last how many pins?");

                // Set up the input
                final EditText input = new EditText(ctx);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        limit = input.getText().toString();

                        if (limit.equals("")) {
                            limit = "5";
                        }

                        GPSOpenHelper goh = new GPSOpenHelper(ctx);
                        Cursor cr = goh.getRecentMarkers(goh, limit);
                        //move ptr to first row
                        cr.moveToFirst();

                        do
                        {
                            System.out.println(cr.getPosition() +  " NAME: " + cr.getString(0)
                                    + ". LAT: " + cr.getString(1) + " LONG: " + cr.getString(2)
                                    + " TIME: " + cr.getString(3));

//                    Marker marker = addMarker(new MarkerOptions());
                            double lat = Double.parseDouble(cr.getString(1));
                            double lng = Double.parseDouble(cr.getString(2));

                            LatLng latLng = new LatLng(lat, lng);
                            markers.add(mMap.addMarker(
                                    new MarkerOptions()
                                            .position(latLng)
                                            .title(cr.getString(0))
                                            .snippet("-Tap: SV -Hold: DEL")
                                            .draggable(true)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                            ));
                        }
                        while (cr.moveToNext());

                        limit = "";

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

//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setRotateGesturesEnabled(true);

        // LatLng Ststephensgreen1 = new LatLng(56.338340, -15.259376);

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
        addMarker(latLng);

        Toast.makeText(this, "-Tap Pin to toggle Info Window On\n" +
                        "-Tap Info Window for StreetView\n" +
                        "-Tap & Hold IW to DELETE Pin",
                Toast.LENGTH_LONG).show();


    }

    public void insertLatlng(LatLng latLng, String markerName)
    {
        GPSOpenHelper db = new GPSOpenHelper(ctx);
        db.insertLatlng(db, latLng, markerName);

    }

    private String markerName;
    int pinNum = 0;
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
                    markerName = "Pin " + (pinNum + 1);
                    pinNum++;
                }

                markers.add(mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title(markerName)
                                .snippet("-Tap: SV -Hold: DEL")
                                .draggable(true)));

                insertLatlng(latLng, markerName);
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

    boolean showInfo = false;

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        showInfo = !showInfo;
        if  (showInfo)
        {
            marker.showInfoWindow();
        }
        else
        {
            marker.hideInfoWindow();
        }

        DecimalFormat df = new DecimalFormat("#0.0");

        if (line != null)
        {
            line.remove();
        }

        userLatLng = new LatLng(gps.location.getLatitude(), gps.location.getLongitude());

        distanceInMeters = SphericalUtil.computeDistanceBetween(userLatLng, marker.getPosition());
        System.out.println("Distance between two points is " + distanceInMeters);

        drawLine(userLatLng, marker);

        Toast.makeText(this, "Pin: " + df.format(distanceInMeters) + "m away.",
                Toast.LENGTH_SHORT).show();

        return true;
    }

    public static Marker destMarker;

    public static void drawLine(LatLng latLng, Marker marker)
    {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        destMarker = null;
        line = mMap.addPolyline(new PolylineOptions()
                .add(latLng).add(markers.get(markers.indexOf(marker)).getPosition())
                .color(color).width(15));
        destMarker = marker;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        int rot = 90;
        int pitch = 0;
        double zoom = 1.0;
        int mapZoom = 8;

        Intent streetIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.streetview:cbll=" + marker.getPosition().latitude + ","
                        + marker.getPosition().longitude + "&cbp=1," + rot + ",," + pitch
                        + "," + zoom + "&mz=" + mapZoom +  ""));

        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(streetIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Verify the intent will resolve to at least one activity
        if (isIntentSafe)
        {
            startActivity(streetIntent);
        }

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

}
