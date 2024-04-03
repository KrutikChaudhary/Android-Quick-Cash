package com.example.group12.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.group12.R;
import com.example.group12.model.Job;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity to display job locations on a map.
 */
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
        List<Job> filteredJobList = new ArrayList<>();

        // Add test jobs to the list
//        Job shoppingCentre = new Job("Security", 20.00F, 2, "ASAP","halifax", "HIGH", 44.649107F, (float) -63.618599);
//        Job fairView = new Job("Dish Washing", 20.00F, 3,"ASAP", "fairview", "LOW", 44.656073F, (float) -63.646536);
//        Job waterFront = new Job("Event Setup", 20.00F, 4, "ASAP", "water front", "HIGH", 44.652058F, (float) -63.586019);
//        filteredJobList.add(shoppingCentre);
//        filteredJobList.add(fairView);
//        filteredJobList.add(waterFront);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        // Add markers for each job location and include them in bounds
        for(int i=0; i<filteredJobList.size(); i++){
            Job temp = filteredJobList.get(i);
            LatLng position = new LatLng(temp.getLatitude(), temp.getLongitude());
            gMap.addMarker(new MarkerOptions().position(position).title(temp.getTitle())
                    .snippet(temp.getSalary() + " - " + temp.getDuration()));
            builder.include(position);
        }

        // Adjust camera to display all markers within bounds with padding
        LatLngBounds bounds = builder.build();
        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        gMap.animateCamera(cu);
    }
}
