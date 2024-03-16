package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
        emailView = findViewById(R.id.TempEmail);
        email = getIntent().getStringExtra("email");
        emailView.setText(email);
        applyJobButtonSetup();
    }

    public void applyJobButtonSetup(){
        applyJob=findViewById(R.id.applyToJob);
        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString();

            }
        });
    }


}