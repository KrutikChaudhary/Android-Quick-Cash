package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.ui.employer.Dashboard_Employer;


public class SelectRoleActivity extends AppCompatActivity {

    String userKey;
    FirebaseDatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userKey = getIntent().getStringExtra("key");
        setContentView(R.layout.activity_select_role);
        employeeRole();
        employerRole();
        dbManager = new FirebaseDatabaseManager();
    }


    protected void employeeRole() {
        Button employee = findViewById(R.id.selectEmployee);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dbManager.updateRole("Employee", userKey);
                //connect to employee dashboard
                Intent a = new Intent(SelectRoleActivity.this, Dashboard_User.class);
                startActivity(a);
                //Test for changing pages
             //   if (a.getName().equals(MainActivity.class)) {
               //     Toast.makeText(SelectRoleActivity.this, "Changed page to employee dashboard", Toast.LENGTH_SHORT).show();
            //    } else {
               //     Toast.makeText(SelectRoleActivity.this, "Error", Toast.LENGTH_SHORT).show();
             //   }
            }
        });
    }
        protected void employerRole() {
            Button employer = findViewById(R.id.selectEmployer);

            employer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dbManager.updateRole("Employer", userKey);
                    //connect to employer dashboard
                    Intent a = new Intent(SelectRoleActivity.this, Dashboard_Employer.class);
                    startActivity(a);

                    //Test for changing pages
                //    if (a instanceof MainActivity.class) {
                 //       Toast.makeText(SelectRoleActivity.this, "Changed page to employer dashboard", Toast.LENGTH_SHORT).show();
                  //  } else {
                 //       Toast.makeText(SelectRoleActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  //  }
                }
            });
        }
}