package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.location.Address;
import com.google.firebase.database.DatabaseReference;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.core.Constants;
import com.example.group12.ui.Dashboard_Employer;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

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

                String jobTitle = title.getText().toString();
                String date = jobDate.getText().toString();
                int expectedDuration = Integer.parseInt(duration.getText().toString());
                String urgency = jobUrgency.getText().toString();
                float salary = Float.parseFloat(jobSalary.getText().toString());
                String jobLocation = location.getText().toString();

                float latitude = 0;
                float longitude = 0;

                Geocoder geocoder = new Geocoder(Dashboard_Employer_PostJob.this);
                List <Address> addresses;

                try {
                    addresses = geocoder.getFromLocationName(jobLocation,1);
                    if(addresses != null){
                        Address address = addresses.get(0);
                        latitude = (float)address.getLatitude();
                        longitude = (float)address.getLongitude();
                    }
                    else{
                        Toast.makeText(Dashboard_Employer_PostJob.this, "No location found for the address provided.", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(Dashboard_Employer_PostJob.this, "No location found for the address provided.", Toast.LENGTH_SHORT).show();
                }

                // Check if any field is empty
                if(jobTitle.isEmpty() || date.isEmpty() || expectedDuration == 0 || urgency.isEmpty() || salary == 0 || jobLocation.isEmpty()) {
                    // Display toast for incomplete fields
                    Toast.makeText(Dashboard_Employer_PostJob.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are entered, proceed to save to Firebase
                    saveJobsToFirebase(jobTitle, date, expectedDuration, urgency, salary, jobLocation, latitude, longitude );

                    // Display success message
                    Toast.makeText(Dashboard_Employer_PostJob.this, "Job uploaded successfully", Toast.LENGTH_SHORT).show();

                    // Navigating back to the your Jobs page
                    Intent yourJobIntent = new Intent(Dashboard_Employer_PostJob.this, Dashboard_Employer.class);
                    Dashboard_Employer_PostJob.this.startActivity(yourJobIntent);
                }
            }
        });
    }


    public void saveJobsToFirebase(String jobTitle, String date, int expectedDuration, String urgency, float salary, String jobLocation, float latitude, float longitude){
        dbManager.saveJobsToFirebase(jobTitle, date, expectedDuration, urgency, salary, jobLocation, latitude, longitude);
    }

    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }
}