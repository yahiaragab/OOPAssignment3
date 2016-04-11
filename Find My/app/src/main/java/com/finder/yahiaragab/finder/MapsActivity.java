package com.finder.yahiaragab.finder;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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

public class MapsActivity extends FragmentActivity
        implements OnMapClickListener, OnMapLongClickListener, OnMapReadyCallback, OnTouchListener {

    private GoogleMap mMap;
    private SensorManager sensormanager;
    private Sensor accelerometer;
    private float last_x, last_y, last_z;
    long lastUpdate = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);

        // Add a marker in Sydney and move the camera
        LatLng Ststephensgreen = new LatLng(53.338340, -6.259376);
        // LatLng Ststephensgreen1 = new LatLng(56.338340, -15.259376);
        float zoomlevel = 15;
        //adds a marker at the lat lng of st stephens green
        mMap.addMarker(new MarkerOptions().position(Ststephensgreen).title("Your tent").draggable(true));
        //sets where camera starts and the zoom level of the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Ststephensgreen, zoomlevel));
        // user location showed
        mMap.setMyLocationEnabled(true);
        //new zoom options
//        UiSettings.setZoomControlsEnabled(true);


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
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Your tent").draggable(true));
    }

}
