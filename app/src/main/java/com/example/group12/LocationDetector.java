package com.example.group12;

import android.app.Activity;
import android.content.Context;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;
import android.location.Location;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.core.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationDetector extends AppCompatActivity {
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationRequest locationRequest;
    private final Context context;
    private LocationInfo locationInfo;
    FirebaseDatabaseManager firebaseDatabaseManager;
    FirebaseDatabase db;
    private static final int REQUEST_CODE = 100;

    public LocationDetector(Context context) {
        this.context = context;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(10000); // 10 seconds
        this.locationRequest.setFastestInterval(5000); // 5 seconds
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        firebaseDatabaseManager = new FirebaseDatabaseManager(db);

        initializeLocationCallback();
    }

    /*
     * Initializes and configures the location callback for receiving location updates.
     * If location access permission is already granted, it starts requesting location updates.
     * Otherwise, it requests the necessary location permission from the user.
     */
    private void initializeLocationCallback() {
        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                    onLocationUpdated(locationResult.getLocations().get(0));
                } else {
                    onLocationUpdateFailed();
                }
            }
        };

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }


    /*
     * Handles the location update event. Converts the Location object to a String
     * using the Geocoder class, logs the address details, and saves the location into location info object.
     * calls save to firebase function to save to database
     * If the geocoding process fails, a RuntimeException is thrown.
     *
     * @param location The Location object containing the new location data.
     */
    public void onLocationUpdated(Location location) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("Address: ","Latitude :" + addresses.get(0).getLatitude() + "\nLongitude :" + addresses.get(0).getLongitude() +
                "\nAddress :" + addresses.get(0).getAddressLine(0) + "\nCity :" + addresses.get(0).getLocality() +
                "\nCountry :" + addresses.get(0).getCountryCode());

        //save to location info object
        this.locationInfo = new LocationInfo(addresses.get(0).getLatitude(),addresses.get(0).getLongitude(),
                addresses.get(0).getAddressLine(0),addresses.get(0).getLocality(),addresses.get(0).getCountryCode());

        saveToFirebase(locationInfo);
    }


    /*
     * This method calls save location to firebase method in firebase manager class
     *  To perform save operation
     * @param locationInfo Location info objects which stores the location
     * @return the result of the operation
     */
    public void saveToFirebase(LocationInfo locationInfo){
        firebaseDatabaseManager.saveLocationToFirebase(locationInfo);
    }

    public void onLocationUpdateFailed() {
        Log.e("DetectionError", "Failed to process location");
    }

    public void stopLocationUpdates(LocationCallback locationCallback) {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    public LocationInfo getLocationInfo(){
        return this.locationInfo;
    }

    public static int getRequestCode() {
        return REQUEST_CODE;
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}

