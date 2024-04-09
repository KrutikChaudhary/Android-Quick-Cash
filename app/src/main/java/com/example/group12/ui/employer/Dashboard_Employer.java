package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.model.Job;
import com.example.group12.util.adapter.EmployerJobAdapter;
import com.example.group12.util.WrapLinearLayoutManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Activity class for the employer dashboard.
 * This activity provides functionality for the employer dashboard,
 * including buttons for posting jobs and paying employees.
 */
public class Dashboard_Employer extends AppCompatActivity {
    Button manageEmployee; // Button to navigate to the pay employees activity
    RecyclerView recyclerView;
    EmployerJobAdapter viewJobAdapter;
    String email;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        init();
        viewJobs();
        // Set up UI components
        setupManageEmployeeButton();
        postJobButtonSetup();
        viewStatsButton();
    }

    /**
     * Initializes the RecyclerView and other necessary components.
     */
    protected void init(){
        recyclerView = findViewById(R.id.employer_jobs_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        email = preferences.getString("email", "");
        Log.d("Email",email);
    }

    /**
     * Retrieves and displays the list of jobs from the Firebase Realtime Database.
     */
    protected void viewJobs(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK)
                .getReference().child("Job");
        Query query = databaseReference.orderByChild("employerEmail").equalTo(email);
        // Configure FirebaseRecyclerOptions to retrieve job applications from Firebase Database

        final FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(query, Job.class).build();

        viewJobAdapter = new EmployerJobAdapter(options,email);
        recyclerView.setAdapter(viewJobAdapter);
    }

    /**
     * Set up the "Post Job" button.
     * This button allows the employer to navigate to the activity for posting a new job.
     */
    protected void postJobButtonSetup() {
        Button postJobButton = findViewById(R.id.postJobButton);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the post job activity
                Intent intent = new Intent(Dashboard_Employer.this, Dashboard_Employer_PostJob.class);
                Dashboard_Employer.this.startActivity(intent);
            }
        });
    }

    /**
     * Set up the "Pay Employees" button.
     * This button allows the employer to navigate to the activity for paying employees.
     */
    public void setupManageEmployeeButton(){
        manageEmployee = findViewById(R.id.manageEmployee);

        manageEmployee.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                // Navigate to the pay employees activity
                Intent intent = new Intent(Dashboard_Employer.this, Dashboard_Employer_ManageEmployee.class);
                Dashboard_Employer.this.startActivity(intent);
            }
        });
    }

    /**
     * Sets up the functionality for the "View Stats" button in the employer dashboard activity.
     * When the button is clicked, it navigates to the employer view stats activity.
     */
    protected void viewStatsButton() {
        Button viewStatsButton = findViewById(R.id.employerViewStats);
        viewStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the post job activity
                Intent intent = new Intent(Dashboard_Employer.this, Dashboard_Employer_View_Stats.class);
                Dashboard_Employer.this.startActivity(intent);
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