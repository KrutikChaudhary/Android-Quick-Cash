package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.Dashboard_User_MyPayPal;
import com.example.group12.model.Job;
import com.example.group12.util.JobAdapter;
import com.example.group12.R;
import com.example.group12.util.WrapLinearLayoutManager;
import com.example.group12.core.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard_User extends AppCompatActivity {

    RecyclerView recyclerView;
    JobAdapter viewJobAdapter;
    Button myPayPal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user);
        init();
        viewJobs();
        myPayPal = findViewById(R.id.PayPalButton);

        myPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard_User.this, Dashboard_User_MyPayPal.class);
                Dashboard_User.this.startActivity(intent);
            }
        });
    }

    protected void init(){
        recyclerView = findViewById(R.id.job_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    protected void viewJobs(){
        final FirebaseRecyclerOptions<Job> options = new FirebaseRecyclerOptions.Builder<Job>()
                .setQuery(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK)
                        .getReference().child("Job"), Job.class).build();
        viewJobAdapter = new JobAdapter(options);
        recyclerView.setAdapter(viewJobAdapter);
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