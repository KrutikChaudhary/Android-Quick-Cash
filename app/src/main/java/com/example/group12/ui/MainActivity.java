package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.locationDetection.LocationDetector;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * The main activity of the application, responsible for handling user registration and login.
 */
public class MainActivity extends AppCompatActivity{

    LocationDetector locationDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up register button
        registerButtonSetup();

        // Set up login button
        loginButtonSetup();

        // Initialize location detector
        initializeLocationDetector();
    }

    /**
     * Set up the register button.
     */
    protected void registerButtonSetup(){
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start RegisterActivity when the register button is clicked
                Intent registerIntent = new Intent(MainActivity.this, SelectRoleActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

    }

    /**
     * Set up the login button.
     */
    protected void loginButtonSetup(){
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LogInActivity when the login button is clicked
                Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });
    }

    /**
     * Test method to create a sample job entry in Firebase.
     */
    public void testJob() {
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        DatabaseReference dbref = db.getReference("Job");
        Map<String, Object> job = new HashMap<>();
        job.put("title", "Dish Washing");
        job.put("duration", "2 hours");
        job.put("salary", "20$ per hour");
        job.put("longitude", 3.23);
        job.put("latitude", 5.5);
    }

    /**
     * Initialize the location detector.
     */
    public void initializeLocationDetector(){
        locationDetector = new LocationDetector(this);
    }

}