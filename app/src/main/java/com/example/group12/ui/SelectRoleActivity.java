package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.ui.employer.Dashboard_Employer;
import com.example.group12.ui.user.Dashboard_User;

/**
 * Activity for selecting user role.
 */
public class SelectRoleActivity extends AppCompatActivity {

    String userKey;
    FirebaseDatabaseManager dbManager;
    private SharedPreferences preferences;

    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        userKey = preferences.getString("key", "");
        setContentView(R.layout.activity_select_role);
        employeeRole();
        employerRole();
        dbManager = new FirebaseDatabaseManager();
    }

    /**
     * Sets up the button for selecting the employee role.
     */
    protected void employeeRole() {
        Button employee = findViewById(R.id.selectEmployee);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dbManager.updateRole("Employee", userKey);
                updateUserInfo("Employee");
                Intent a = new Intent(SelectRoleActivity.this, Dashboard_User.class);
                startActivity(a);
            }
        });
    }

    /**
     * Sets up the button for selecting the employer role.
     */
    protected void employerRole() {
        Button employer = findViewById(R.id.selectEmployer);

        employer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dbManager.updateRole("Employer", userKey);
                updateUserInfo("Employer");
                Intent a = new Intent(SelectRoleActivity.this, Dashboard_Employer.class);
                startActivity(a);
            }
        });
    }

    private void updateUserInfo(String role){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("role", role);
        editor.apply();
    }
}