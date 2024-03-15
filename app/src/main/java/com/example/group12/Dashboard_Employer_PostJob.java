package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.core.Constants;
import com.example.group12.ui.Dashboard_Employer;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard_Employer_PostJob extends AppCompatActivity {

    FirebaseDatabaseManager dbManager;
    EditText title, duration, jobUrgency, jobSalary, location, jobDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_post_job);
        databaseInit();
        //initialize edit text fields
        title = findViewById(R.id.editTextJobTitle);
        jobDate = findViewById(R.id.editTextDate);
        duration = findViewById(R.id.editTextExpectedDuration);
        jobUrgency = findViewById(R.id.editTextUrgency);
        jobSalary = findViewById(R.id.editTextSalary);
        location = findViewById(R.id.editTextJobLocation);

        YourJobButtonSetup();
        postJobButtonSetup();
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



    public void postJobButtonSetup(){
        Button postJobButton = findViewById(R.id.sendToDatabaseButton);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String jobTitle =title.getText().toString();
                String date = jobDate.getText().toString();
                String expectedDuration =duration.getText().toString();
                String urgency =jobUrgency.getText().toString();
                String salary = jobSalary.getText().toString();
                String jobLocation = location.getText().toString();


                saveJobsToFirebase(jobTitle, date, expectedDuration, urgency, salary, jobLocation);

            }
        });
    }

    public void saveJobsToFirebase(String jobTitle, String date, String expectedDuration, String urgency, String salary, String jobLocation){
        dbManager.saveJobsToFirebase(jobTitle, date, expectedDuration, urgency, salary, jobLocation);
    }

    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }
}