package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group12.LocationDetector;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    LocationDetector locationDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerButtonSetup();
        loginButtonSetup();
        testJob();
    }

    protected void registerButtonSetup(){
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

    }

    protected void loginButtonSetup(){
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LogInActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });
    }

    public void testJob(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        DatabaseReference dbref = db.getReference("Job");
        Map<String, Object> job = new HashMap<>();
        job.put("title", "Dish Washing");
        job.put("Duration", "2 hour");
        job.put("Salary", "50$");
        job.put("Start Date", "today");
        job.put("Urgency", "Urgent");

        dbref.push().setValue(job);
    }


}