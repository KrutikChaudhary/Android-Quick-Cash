package com.example.group12.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group12.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * An activity to display job locations on a map.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    float jobLatitude;
    float jobLongitude;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        jobLatitude = getIntent().getFloatExtra("latitude",0);
        jobLongitude = getIntent().getFloatExtra("longitude", 0);
        title = getIntent().getStringExtra("title");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, move camera, zoom in/out, etc.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        // Check if the jobLatitude and jobLongitude are valid
        if (jobLatitude != 0 && jobLongitude != 0) {
            // Create a LatLng object from the latitude and longitude values
            LatLng jobLocation = new LatLng(jobLatitude, jobLongitude);

            // Add a marker for the job location
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(jobLocation)
                    .title(title); // Use the job title as the marker title

            Marker marker = gMap.addMarker(markerOptions);

            // Ensure the marker is not null, then show its info window
            if (marker != null) {
                marker.showInfoWindow();
            }
            // Move the camera to focus on the job location with an appropriate zoom level
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(jobLocation, 15));
        }
    }
}
