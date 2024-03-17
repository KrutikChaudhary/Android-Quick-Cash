package com.example.group12.maps;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.group12.R;
import com.example.group12.model.Job;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.group12.databinding.ActivityMapsBinding;

import java.io.IOException;
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
        String location = "Halifax Shopping Centre";
        List<Job> filteredJobList = new ArrayList<>();

        // add test jobs to the list
        Job shoppingCentre = new Job("Security", "20$/h", "2 hours", "ASAP",  "Halifax Shopping Centre");
        Job fairView = new Job("Dish Washing",  "18$/h","1 hours", "ASAP",  "Dalhousie University");
        Job waterFront = new Job("Event Setup", "18$/h","6 hours",  "ASAP","MicMac Mall halifax");
        filteredJobList.add(shoppingCentre);
        filteredJobList.add(fairView);
        filteredJobList.add(waterFront);


        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> addressList = null;
        //initialize map
        mMap = googleMap;
        for(int i=0; i<filteredJobList.size(); i++){
            try{
                addressList = geocoder.getFromLocationName(filteredJobList.get(i).getLocation(),1);
                if (!addressList.isEmpty()) {
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(filteredJobList.get(i).getTitle()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!addressList.isEmpty()) {
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }

    }
}