package com.example.mylaurier;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.*;
import com.google.maps.android.data.kml.KmlLayer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String TAG = "myMap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        LatLng laurierWaterloo = new LatLng(43.4723571, -80.5285286);
        //mMap.addMarker(new MarkerOptions().position(laurierWaterloo).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(laurierWaterloo));
        // change camera zoom level, without changing position
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        // add custom map layer
        KmlLayer layer;
        try {
            layer = new KmlLayer(mMap, R.raw.laurier, getApplicationContext());
            layer.addLayerToMap();
        }
        catch (Exception e){

        }
        checkLocationPermissions();
    }

    /*
    check user permissions, this should probably be in main activity
     */
    public void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted
            Log.d(TAG, "Permission for Location not granted, unable to locate user");
            Snackbar mySnackbar = Snackbar.make(getWindow().getDecorView().getRootView(),"Unable to determine location due to location permissions.", 10);
            mySnackbar.show();
        }
    }
}
