package com.example.group12.ui;

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
public class Dashboard_Employer_PayEmployee extends AppCompatActivity {
    TextView listOfEmployees;
    RecyclerView recyclerView;
    JobApplicationAdapter jobApplicationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_pay_employee);
//        listOfEmployees = findViewById(R.id.listOfEmployees);
        init();
        viewApplications();
    }
    protected void init(){
        recyclerView = findViewById(R.id.job_application_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    protected void viewApplications(){
        final FirebaseRecyclerOptions<JobApplication> options = new FirebaseRecyclerOptions.Builder<JobApplication>()
                .setQuery(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK)
                        .getReference().child("Job Application"), JobApplication.class).build();
        jobApplicationAdapter = new JobApplicationAdapter(options);
        recyclerView.setAdapter(jobApplicationAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        jobApplicationAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        jobApplicationAdapter.stopListening();
    }
}