package com.example.group12.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.R;

public class Dashboard_User_PreferredJobs extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "UserPreferences";
    private static final String KEY_LOCATION = "preferred_location";
    private static final String KEY_SALARY = "preferred_salary";
    private static final String KEY_JOB_TITLE = "preferred_job_title";

    private EditText editTextPreferredLocation;
    private EditText editTextPreferredSalary;
    private EditText editTextPreferredJobTitle;
    private Button buttonSubmit;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_preferred_jobs);

        // Initialize SharedPreferences
        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // Initialize views
        editTextPreferredLocation = findViewById(R.id.editTextPreferredLocation);
        editTextPreferredSalary = findViewById(R.id.editTextPreferredSalary);
        editTextPreferredJobTitle = findViewById(R.id.editTextPreferredJobTitle);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Load saved preferences if available
        loadPreferences();

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(view -> savePreferences());
    }

    // Load saved preferences
    private void loadPreferences() {
        String location = preferences.getString(KEY_LOCATION, "");
        String salary = preferences.getString(KEY_SALARY, "");
        String jobTitle = preferences.getString(KEY_JOB_TITLE, "");

        editTextPreferredLocation.setText(location);
        editTextPreferredSalary.setText(salary);
        editTextPreferredJobTitle.setText(jobTitle);
    }

    // Save preferences
    private void savePreferences() {
        String location = editTextPreferredLocation.getText().toString();
        String salary = editTextPreferredSalary.getText().toString();
        String jobTitle = editTextPreferredJobTitle.getText().toString();

        // Check if at least one field is filled
        if (location.isEmpty() && salary.isEmpty() && jobTitle.isEmpty()) {
            Toast.makeText(this, "Please enter at least one preference", Toast.LENGTH_SHORT).show();
            return; // Stop execution if no preference is entered
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_LOCATION, location);
        editor.putString(KEY_SALARY, salary);
        editor.putString(KEY_JOB_TITLE, jobTitle);
        editor.apply();

        Toast.makeText(this, "Preferences saved successfully", Toast.LENGTH_SHORT).show();
    }
}