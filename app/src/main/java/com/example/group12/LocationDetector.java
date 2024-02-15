package com.example.group12;


import android.content.Context;
import android.location.Location;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationDetector extends AppCompatActivity {
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationRequest locationRequest;
    //private final LocationUpdatesListener listener;
    private final Context context;
    private static final int REQUEST_CODE = 100;

    public LocationDetector(Context context) {
        this.context = context;
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setInterval(10000); // 10 seconds
        this.locationRequest.setFastestInterval(5000); // 5 seconds
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        initializeLocationCallback();
    }

    private void initializeLocationCallback() {
        //IMPLEMENT
    }

    public void stopLocationUpdates(LocationCallback locationCallback) {
        //IMPLEMENT
    }

    public void onLocationUpdated(Location location) {
        //IMPLEMENT
    }


    public void onLocationUpdateFailed() {
        //IMPLEMENT
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}

