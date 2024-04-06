package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.group12.R;

public class EmployerJobDetail extends AppCompatActivity {
    String title;
    String desc;
    TextView jobTitle;
    TextView jobDesc;
    Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_job_detail);
        jobTitle = findViewById(R.id.EmployerJobDetailsTitle);
        jobDesc = findViewById(R.id.EmployerJobDescription);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        setTextView();
        setupGoBackButton();
    }
    /**
     * Sets the text views with job title and description.
     */
    public void setTextView(){
        jobTitle.setText(title);
        jobDesc.setText(desc);
    }

    public void setupGoBackButton(){
        goBackButton = findViewById(R.id.buttonGoBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerJobDetail.this, Dashboard_Employer.class);
                EmployerJobDetail.this.startActivity(intent);
            }
        });
    }
}