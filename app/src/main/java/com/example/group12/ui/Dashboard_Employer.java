package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.Dashboard_Employer_PostJob;
import com.example.group12.R;

/**
 * Activity class for the employer dashboard.
 */
public class Dashboard_Employer extends AppCompatActivity {
    Button payEmployeesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer);
        setupPayEmployeeButton();
    }
    public void setupPayEmployeeButton(){
        payEmployeesButton = findViewById(R.id.payEmployee);

        payEmployeesButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Dashboard_Employer.this, Dashboard_Employer_PayEmployee.class);
                Dashboard_Employer.this.startActivity(intent);
            }
        });
    }
}