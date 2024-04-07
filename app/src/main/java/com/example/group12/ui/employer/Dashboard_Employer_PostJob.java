package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Address;

import com.example.group12.firebase.crud.FirebaseCreateManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

/**
 * Activity class for posting a job from the employer dashboard.
 */
public class Dashboard_Employer_PostJob extends AppCompatActivity {

    // Firebase database manager instance
    FirebaseCreateManager dbManager;

    // EditText fields for job details
    EditText title, duration, jobUrgency, jobSalary, location, jobDate;
    
    String employerEmail;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_post_job);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        employerEmail = preferences.getString("email", "");
        // Initialize Firebase database manager
        databaseInit();

        // Initialize EditText fields
        title = findViewById(R.id.editTextJobTitle);
        jobDate = findViewById(R.id.editTextDate);
        duration = findViewById(R.id.editTextExpectedDuration);
        jobUrgency = findViewById(R.id.editTextUrgency);
        jobSalary = findViewById(R.id.editTextSalary);
        location = findViewById(R.id.editTextJobLocation);

        // Setup button for navigating to the employer's jobs page
        YourJobButtonSetup();

        // Setup button for posting a job
        postJobButtonSetup();
    }

    /**
     * Sets up the button to navigate to the employer's jobs page.
     */
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

    /**
     * Sets up the button for posting a job.
     */
    public void postJobButtonSetup(){
        Button postJobButton = findViewById(R.id.sendToDatabaseButton);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve job details from EditText fields
                String jobTitle = title.getText().toString();
                String date = jobDate.getText().toString();
                int expectedDuration = Integer.parseInt(duration.getText().toString());
                String urgency = jobUrgency.getText().toString();
                float salary = Float.parseFloat(jobSalary.getText().toString());
                String jobLocation = location.getText().toString();

                float latitude = 0;
                float longitude = 0;

                // Get latitude and longitude of the job location
                Geocoder geocoder = new Geocoder(Dashboard_Employer_PostJob.this);
                List<Address> addresses;

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
                    saveJobsToFirebase(employerEmail, jobTitle, date, expectedDuration, urgency, salary, jobLocation, latitude, longitude );

                    // Display success message
                    Toast.makeText(Dashboard_Employer_PostJob.this, "Job uploaded successfully", Toast.LENGTH_SHORT).show();

                    // Navigating back to the your Jobs page
                    Intent yourJobIntent = new Intent(Dashboard_Employer_PostJob.this, Dashboard_Employer.class);
                    Dashboard_Employer_PostJob.this.startActivity(yourJobIntent);
                }
            }
        });
    }

    /**
     * Saves job details to Firebase.
     * @param jobTitle Title of the job
     * @param date Date of the job
     * @param expectedDuration Expected duration of the job
     * @param urgency Urgency of the job
     * @param salary Salary of the job
     * @param jobLocation Location of the job
     * @param latitude Latitude of the job location
     * @param longitude Longitude of the job location
     */
    public void saveJobsToFirebase(String employerEmail, String jobTitle, String date, int expectedDuration, String urgency, float salary, String jobLocation, float latitude, float longitude){
        dbManager.saveJobsToFirebase(employerEmail, jobTitle, date, expectedDuration, urgency, salary, jobLocation, latitude, longitude);
    }

    /**
     * Initializes the Firebase database.
     */
    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseCreateManager(db);
    }
}