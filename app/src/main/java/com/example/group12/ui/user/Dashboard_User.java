package com.example.group12.ui.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import android.view.View;
import android.content.Intent;

import com.example.group12.model.Job;
import com.example.group12.ui.SearchJobActivity;
import com.example.group12.util.JobAdapter;
import com.example.group12.R;
import com.example.group12.util.WrapLinearLayoutManager;
import com.example.group12.core.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity class for the user dashboard.
 * This activity displays a list of jobs and allows users to search for jobs and access their PayPal account.
 */
public class Dashboard_User extends AppCompatActivity {

    RecyclerView recyclerView;
    JobAdapter viewJobAdapter;
    String key;
    String email;
    Button findJobButton;
    Button myPayPal;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        init();
        viewJobs();
        findJobButtonSetup();
        paypalButtonSetup();
        preferenceButtonSetup();
        viewStatsButtonSetup();
    }

    /**
     * Initializes the RecyclerView and other necessary components.
     */
    protected void init(){
        recyclerView = findViewById(R.id.filteredJob_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        key = preferences.getString("key", "");
        email = preferences.getString("email", "");
    }

    /**
     * Retrieves and displays the list of jobs from the Firebase Realtime Database.
     */
    protected void viewJobs(){
        final FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK)
                        .getReference().child("Job"), Job.class).build();

        viewJobAdapter = new JobAdapter(options,email);
        recyclerView.setAdapter(viewJobAdapter);
    }

    /**
     * Sets up the button for accessing the user's PayPal account.
     */
    public void paypalButtonSetup(){
        myPayPal = findViewById(R.id.PayPalButton);

        myPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, Dashboard_User_MyPayPal.class);
                Dashboard_User.this.startActivity(intent);
            }
        });
    }

    /**
     * Sets up the button for finding jobs.
     */
    protected void findJobButtonSetup(){
        findJobButton = findViewById(R.id.findJobsButton);
        findJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, SearchJobActivity.class);
                Dashboard_User.this.startActivity(intent);
            }
        });
    }

    /**
     * Sets up the button for preference page.
     */
    protected void preferenceButtonSetup(){
        Button myPreference = findViewById(R.id.preferredJobsButton);
        myPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, Dashboard_User_PreferredJobs.class);
                Dashboard_User.this.startActivity(intent);
            }
        });
    }

    /**
     * Sets up the button for stats page.
     */
    protected void viewStatsButtonSetup(){
        Button viewStatsButton = findViewById(R.id.userViewStats);
        viewStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, Dashboard_User_View_Stats.class);
                Dashboard_User.this.startActivity(intent);
            }
        });
    }

    // Lifecycle method called when the activity is started.
    // Start listening for changes in the data and update the UI accordingly.
    @Override
    protected void onStart() {
        super.onStart();
        viewJobAdapter.startListening();
    }

    // Lifecycle method called when the activity is stopped.
    // Stop listening for changes in the data to conserve resources.
    @Override
    protected void onStop() {
        super.onStop();
        viewJobAdapter.stopListening();
    }
}