package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.Dashboard_Employer_PostJob;
import com.example.group12.R;

/**
 * Activity class for the employer dashboard.
 */
public class Dashboard_Employer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);
        // Setup button for posting a job
        postJobButtonSetup();
    }


    /**
     * Sets up the button for posting a job.
     */
    protected void postJobButtonSetup(){
        Button postJobButton = findViewById(R.id.postJobButton);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the post job activity
                Intent loginIntent = new Intent(Dashboard_Employer.this, Dashboard_Employer_PostJob.class);
                Dashboard_Employer.this.startActivity(loginIntent);
            }
        });
    }
}