package com.example.pmccarthy.mapstest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.pmccarthy.mapstest.MathUtil.*;
import static com.example.pmccarthy.mapstest.SphericalUtil.*;


import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
import android.app.Dialog;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        OnMapLongClickListener, OnMarkerClickListener, OnInfoWindowClickListener,
        OnInfoWindowLongClickListener, OnMapClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    Location location;
    double user_lat;
    double user_lng;
    double distanceInMeters= 0;


    GPSTracker gps;
   // SQLiteDatabase mydatabase = openOrCreateDatabase("track_me_back_db",MODE_PRIVATE,null);


    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // HERES THE Top Left Button Code
        Button button = new Button(this);
        button.setText("Click me");
        addContentView(button, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));

        button.setOnClickListener(new View.OnClickListener() {


            // THIS IS WHERE THE DRAW THE LINE FUNCTION SHOULD BE PUT
            @Override
            public void onClick(View v)
            {
                System.out.println("here   " + height);

            }

        });

        //Heres the bottom left Button Code
        // Here you go man heres the code that cereates a second button
        float x1 = 0;
        //float y1 = 100;
        // Looking at trying to add an image to back of button
        //Drawable d = getResources().getDrawable(R.drawable.common_google_signin_btn_text_light);

        final Button button2 = new Button(this);
        button2.setText("Recent Places");
        addContentView(button2, new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT));
        button2.setX(x1);
        button2.setY(height - 180);
        //button2.setBackground(d);
        button2.setOnClickListener(new View.OnClickListener() {


            // THIS IS WHERE THE DRAW Select statement from the database should go.
            @Override
            public void onClick(View v)
            {
                System.out.println("1234");


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
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnInfoWindowLongClickListener(this);

        // Add a marker in Sydney and move the camera

        //user_lat = location.getLatitude();
        //user_lng = location.getLongitude();


        gps = new GPSTracker(MapsActivity.this);


        LatLng userLatLng = new LatLng(gps.getLatitude(), gps.getLongitude());


        LatLng Ststephensgreen1 = new LatLng(53.3371175,-6.2676459);
        float zoomlevel = 15;
        //adds a marker at the lat lng of st stephens green
        //mMap.addMarker(new MarkerOptions().position(userLatLng).title("Your tent").draggable(true));
        //sets where camera starts and the zoom level of the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, zoomlevel));
        // user location showed
        mMap.setMyLocationEnabled(true);


//        double distanceInMeters= 0;
//        distanceInMeters = SphericalUtil.computeDistanceBetween(userLatLng, Ststephensgreen1);
//

//        System.out.println("Distance you are ATTTT  " + userLatLng);
//
//        System.out.println("Distance PIN IS AT  " + Ststephensgreen1);



        //new zoom options
        //UiSettings.setZoomControlsEnabled(true);

       


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
                Uri.parse("android-app://com.example.pmccarthy.mapstest/http/host/path")
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
                Uri.parse("android-app://com.example.pmccarthy.mapstest/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public float xx;
    public float yy;


//    @Override
//    public boolean onTouch(View v, MotionEvent me)
//    {
//        xx = me.getX();
//        yy = me.getY();
//
//        switch (me.getAction())
//        {
//            case MotionEvent.ACTION_DOWN:
//
//                LatLng pin = new LatLng(xx, yy);
//                // LatLng Ststephensgreen1 = new LatLng(56.338340, -15.259376);
//                float zoomlevel = 15;
//                //adds a marker at the lat lng of st stephens green
//                mMap.addMarker(new MarkerOptions().position(pin).title("Your tent").draggable(true));
//                //sets where camera starts and the zoom level of the camera
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pin, zoomlevel));
//
//                break;
//
//
//            case MotionEvent.ACTION_UP:
//
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//
//                break;
//
//
//        }
//
//
//        return true;
//    }

    @Override
    public void onMapClick(LatLng latLng)
    {

    }

    String markerName;

    @Override
    public void onMapLongClick(final LatLng latLng)
    {
        markerName = " ";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New pin name:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        // | InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                markerName = input.getText().toString();


                markers.add(mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title(markerName)
                                .snippet("Tap and hold to delete pin")
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))));
                markerName = " ";

            }
        });

        gps = new GPSTracker(MapsActivity.this);

        LatLng userLatLng = new LatLng(gps.getLatitude(), gps.getLongitude());

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

//        markers.add(
//                mMap.addMarker(new MarkerOptions()
//                        .position(latLng)
//                        .title(markerName)
//                        .snippet("Tap and hold to delete pin")
//                        .draggable(true)));


        for (int i = 0; i < markers.size(); i++)
        {
            System.out.println("Marker " + i + " is at position: " + markers.get(i).getPosition());


        }


    }


    @Override
    public boolean onMarkerClick(Marker marker)
    {
        marker.showInfoWindow();
        return true;
    }


    @Override
    public void onInfoWindowClick(Marker marker)
    {
//        Toast.makeText(this, "Pin deleted",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker)
    {
        marker.remove();
        Toast.makeText(this, "Pin deleted",
                Toast.LENGTH_SHORT).show();
    }




    public void findDist(LatLng userLatLng, Marker marker)
    {
        distanceInMeters = SphericalUtil.computeDistanceBetween(userLatLng, marker.getPosition());
        System.out.println("The distance between user and this marker is " + distanceInMeters + "(m)");
    }


}


