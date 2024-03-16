package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.group12.R;

public class JobApply extends AppCompatActivity {
    EditText fullName;
    Button applyJob;
    String email;
    TextView emailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);
        fullName = findViewById(R.id.applyEnterFullName);
        applyJob=findViewById(R.id.applyToJob);
        emailView = findViewById(R.id.TempEmail);
        email = getIntent().getStringExtra("email");
        emailView.setText(email);
    }
}