package com.example.group12.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard_User_PreferredJobs extends AppCompatActivity {

    private EditText editTextPreferredLocation;
    private EditText editTextPreferredSalary;
    private EditText editTextPreferredJobTitle;
    private Button buttonSubmit;

    private SharedPreferences preferences;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_preferred_jobs);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");

        // Initialize views
        editTextPreferredLocation = findViewById(R.id.editTextPreferredLocation);
        editTextPreferredSalary = findViewById(R.id.editTextPreferredSalary);
        editTextPreferredJobTitle = findViewById(R.id.editTextPreferredJobTitle);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase(key,editTextPreferredLocation.getText().toString(),
                        editTextPreferredSalary.getText().toString(),editTextPreferredJobTitle.getText().toString());
                saveLocally(editTextPreferredLocation.getText().toString(),
                        editTextPreferredSalary.getText().toString(),editTextPreferredJobTitle.getText().toString());
            }
        });
    }

    private void saveToFirebase(String key, String preferredLocation, String preferredSalary, String preferredJobTitle) {
        // Check if any preference field is empty
        if (preferredLocation.isEmpty() && preferredSalary.isEmpty() && preferredJobTitle.isEmpty()) {
            // Show toast message for empty preferences
            Toast.makeText(this, "Failed to submit preference. Enter at least one preference!", Toast.LENGTH_SHORT).show();
        } else {
            // Proceed to save preferences to Firebase
            FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
            FirebaseDatabaseManager firebaseDatabaseManager = new FirebaseDatabaseManager(db);
            firebaseDatabaseManager.savePreferenceToFirebase(key, preferredLocation, preferredSalary, preferredJobTitle);

            // Show success message
            Toast.makeText(this, "Preferences submitted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveLocally(String preferredLocation, String preferredSalary, String preferredJobTitle){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("salary", preferredSalary);
        editor.putString("location", preferredLocation);
        editor.putString("title", preferredJobTitle);
        editor.apply();
    }



}