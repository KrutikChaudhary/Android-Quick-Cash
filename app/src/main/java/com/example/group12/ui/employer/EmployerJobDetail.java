package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.group12.R;

public class EmployerJobDetail extends AppCompatActivity {
    String title;
    String desc;
    TextView jobTitle;
    TextView jobDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_job_detail);
        jobTitle = findViewById(R.id.EmployerJobDetailsTitle);
        jobDesc = findViewById(R.id.EmployerJobDescription);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        setTextView();
    }
    /**
     * Sets the text views with job title and description.
     */
    public void setTextView(){
        jobTitle.setText(title);
        jobDesc.setText(desc);
    }
}