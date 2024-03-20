package com.example.group12.ui.employer;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.model.JobApplication;
import com.example.group12.util.JobApplicationAdapter;
import com.example.group12.util.WrapLinearLayoutManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity class for the employer dashboard to pay employees.
 * This activity displays a list of job applications submitted by employees
 * and allows the employer to pay the selected employee.
 */
public class Dashboard_Employer_PayEmployee extends AppCompatActivity {
    TextView listOfEmployees; // TextView to display the list of employees
    RecyclerView recyclerView; // RecyclerView to display job applications
    JobApplicationAdapter jobApplicationAdapter; // Adapter for job applications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_pay_employee);

        // Initialize UI components and setup RecyclerView
        init();
        viewApplications();
    }

    // Initialize RecyclerView
    protected void init(){
        recyclerView = findViewById(R.id.job_application_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    // Retrieve and display job applications
    protected void viewApplications(){
        // Configure FirebaseRecyclerOptions to retrieve job applications from Firebase Database
        final FirebaseRecyclerOptions<JobApplication> options = new FirebaseRecyclerOptions.Builder<JobApplication>()
                .setQuery(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK)
                        .getReference().child("Job Application"), JobApplication.class).build();
        // Initialize the job application adapter with the retrieved options
        jobApplicationAdapter = new JobApplicationAdapter(options);
        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(jobApplicationAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start listening for changes in the adapter
        jobApplicationAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Stop listening for changes in the adapter
        jobApplicationAdapter.stopListening();
    }
}