package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.ui.Dashboard_Employer;

public class Dashboard_Employer_PostJob extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_post_job);
        YourJobButtonSetup();
    }

    protected void YourJobButtonSetup() {
        Button yourJobButton = findViewById(R.id.yourJobsButton);
        yourJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yourJobIntent = new Intent(Dashboard_Employer_PostJob.this, Dashboard_Employer.class);
                Dashboard_Employer_PostJob.this.startActivity(yourJobIntent);
            }
        });
    }
}