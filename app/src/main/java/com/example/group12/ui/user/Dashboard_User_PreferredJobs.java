package com.example.group12.ui.user;

import android.os.Bundle;
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

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_preferred_jobs);

        key = getIntent().getStringExtra("key");

        // Initialize views
        editTextPreferredLocation = findViewById(R.id.editTextPreferredLocation);
        editTextPreferredSalary = findViewById(R.id.editTextPreferredSalary);
        editTextPreferredJobTitle = findViewById(R.id.editTextPreferredJobTitle);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(view -> saveToFirebase(key,editTextPreferredLocation.getText().toString(),
                editTextPreferredSalary.getText().toString(),editTextPreferredJobTitle.getText().toString()));
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

}