package com.example.group12.ui.loginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.group12.R;

/**
 * Activity for selecting user role.
 */
public class SelectRoleActivity extends AppCompatActivity {

    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        employeeRole();
        employerRole();
    }

    /**
     * Sets up the button for selecting the employee role.
     */
    protected void employeeRole() {
        Button employee = findViewById(R.id.selectEmployee);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent a = new Intent(SelectRoleActivity.this, RegisterActivity.class);
                a.putExtra("Role", "Employee");
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

                Intent a = new Intent(SelectRoleActivity.this, RegisterActivity.class);
                a.putExtra("Role", "Employer");
                startActivity(a);
            }
        });
    }

}