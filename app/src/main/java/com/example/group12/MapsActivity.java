package com.example.group12;

import

        androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.group12.model.Job;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.group12.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     *
     * This function displays/locates the list of jobs on google maps
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Job> filteredJobList = new ArrayList<>();
        mMap = googleMap;

        // add test jobs to the list
        Job shoppingCentre = new Job("Security", "2 hours", "20$/h", "ASAP", "HIGH", 44.649107, -63.618599);
        Job fairView = new Job("Dish Washing", "1 hours", "18$/h", "ASAP", "LOW", 44.656073, -63.646536);
        Job waterFront = new Job("Event Setup", "6 hours", "18$/h", "ASAP", "HIGH", 44.652058, -63.586019);
        filteredJobList.add(shoppingCentre);
        filteredJobList.add(fairView);
        filteredJobList.add(waterFront);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(int i=0; i<filteredJobList.size(); i++){
            Job temp = filteredJobList.get(i);
            LatLng position = new LatLng(temp.getLat(), temp.getLng());
            mMap.addMarker(new MarkerOptions().position(position).title(temp.getTitle())
                    .snippet(temp.getSalary() + " - " + temp.getDuration()));
            builder.include(position);
        }
        LatLngBounds bounds = builder.build();
        int padding = 100;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.animateCamera(cu);
    }
}