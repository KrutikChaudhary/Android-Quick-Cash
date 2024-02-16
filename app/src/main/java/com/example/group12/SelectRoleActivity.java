package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectRoleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
    }

    protected void employeeRole(){
        Button employee = findViewById(R.id.selectEmployee);
        Button employer = findViewById(R.id.selectEmployer);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //connect to employee dashboard
                Intent a = new Intent(SelectRoleActivity.this, DashBoard_User);
                startActivity(a);
                //add role to database
            }
        }
    }
    protected void employerRole(){
    Button employer = findViewById(R.id.selectEmployer);

        employer.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            //connect to employer dashboard
            Intent a = new Intent(SelectRoleActivity.this, DashBoard_Employer);
            startActivity(a);
            //add role to database

        }
    }
}
}