package com.example.mylaurier;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.data.kml.KmlLayer;


public class MapFragment extends Fragment implements OnMapReadyCallback {


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
    private Context mContext;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        // initialize the map variable
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLng(laurierWaterloo));
        // change camera zoom level, without changing position
        mMap.moveCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM));

        //
        this.mLocationPermissionGranted = checkLocationPermissions();

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
            buildings = new KmlLayer(mMap, R.raw.buildings, this.mContext);
            foodstores = new KmlLayer(mMap, R.raw.foodstores, this.mContext);
            buildings.addLayerToMap();
            foodstores.addLayerToMap();
        }
        catch (Exception e) {
            Log.d(TAG, "could not load KML Layer");
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.mContext);

        // setup toolbar
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            Log.d(TAG, "Trying to update Location");
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private boolean checkLocationPermissions() {
        Log.d(TAG, "checking if user granted permission");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // permission not granted
            Log.d(TAG, "App does not have location permissions");
            // inner class for snackbar button
            class ChangeLocationListener implements View.OnClickListener {
                @Override
                public void onClick(View v) {
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            }
            Snackbar mySnackbar = Snackbar.make(getView(), "Allow app to access location?", Snackbar.LENGTH_LONG);
            mySnackbar.setAction("Allow", new ChangeLocationListener());
            mySnackbar.show();
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Permission changed");
        //mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    Log.d(TAG, "Permission Granted");
                    getDeviceLocation();
                    updateLocationUI();
                }
            }
        }

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Log.d(TAG, "getDeviceLocation");
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
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
}