package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.R;

/**
 * Activity class for the employer dashboard.
 * This activity provides functionality for the employer dashboard,
 * including buttons for posting jobs and paying employees.
 */
public class Dashboard_Employer extends AppCompatActivity {
    Button manageEmployee; // Button to navigate to the pay employees activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);

        // Set up UI components
        setupManageEmployeeButton();
        postJobButtonSetup();
        viewStatsButton();
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

}