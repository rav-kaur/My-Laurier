package com.example.mylaurier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.*;
import com.google.maps.android.data.kml.KmlLayer;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap;
    private static final String TAG = "myMap";
    private FusedLocationProviderClient mFusedLocationProviderClient = null;
    private Location mLastKnownLocation = null;
    private boolean mLocationPermissionGranted = false;
    private LatLng laurierWaterloo = new LatLng(43.4723571, -80.5285286);
    private boolean showBuildings = true, showFoodStores = true;
    private KmlLayer buildings, foodstores;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // setup toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        // initialize the map variable
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(laurierWaterloo));
        // change camera zoom level, without changing position
        mMap.moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        // add custom map layer
//        KmlLayer layer;
//        try {
//            layer = new KmlLayer(mMap, R.raw.laurier, getApplicationContext());
//            layer.addLayerToMap();
//        }
//        catch (Exception e){
//            Log.d(TAG, "could not load KML Layer");
//        }
        // KML Layers

        try {
            buildings = new KmlLayer(mMap, R.raw.buildings, getApplicationContext());
            foodstores = new KmlLayer(mMap, R.raw.foodstores, getApplicationContext());
            buildings.addLayerToMap();
            foodstores.addLayerToMap();
        }
        catch (Exception e) {
            Log.d(TAG, "could not load KML Layer");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gmapmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.btnBuilding:
                if (showBuildings == false){
                    showBuildings = true;
                    item.setTitle("Hide Buildings");
                    try{
                        buildings.addLayerToMap();
                    }
                    catch (Exception e) {
                        Log.d(TAG, "could not load building layer onto map");
                    }
                }
                else {
                    showBuildings = false;
                    item.setTitle("Show Buildings");
                    buildings.removeLayerFromMap();
                }
                break;
            case R.id.btnFoodStores:
                if (showFoodStores == false){
                    showFoodStores = true;
                    item.setTitle("Hide Food/ Stores");
                    try {
                        foodstores.addLayerToMap();
                    }
                    catch (Exception e) {
                        Log.d(TAG, "could not load food/ store layer onto map");
                    }
                }
                else{
                    showFoodStores = false;
                    item.setTitle("Show Food/ Stores");
                    foodstores.removeLayerFromMap();
                }
                break;
        }
        return true;
        //return super.onOptionsItemSelected(item);
    }

    /*
            check user permissions, this should probably be in main activity
             */
    private boolean checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted
            Log.d(TAG, "App does not have location permissions");
            final Activity temp = this;
            // inner class for snackbar button
            class ChangeLocationListener implements View.OnClickListener {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(temp,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            }
            Snackbar mySnackbar = Snackbar.make(this.getWindow().getDecorView().findViewById(android.R.id.content), "Allow app to access location?", Snackbar.LENGTH_LONG);
            mySnackbar.setAction("Allow", new ChangeLocationListener());
            mySnackbar.show();
            return false;
        }
        return true;
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location)task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            // default location
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(laurierWaterloo, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                checkLocationPermissions();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
