package com.example.group12.ui.user.JobApplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group12.maps.MapsActivity;
import com.example.group12.ui.user.Dashboard_User_MyPayPal;
import com.example.group12.R;
import com.example.group12.util.MerchantIDCallBack;
import com.example.group12.logic.MerchantIDValidator;

/**
 * Activity class for displaying job details and applying for a job.
 * This activity displays the details of a job and allows users to apply for it.
 */
public class JobDetailActivity extends AppCompatActivity {
    String title;
    String desc;
    String email;
    TextView jobTitle;
    TextView jobDesc;
    Button apply;
    Button viewOnMaps;
    String employerEmail;
    float jobLatitude;
    float jobLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        jobTitle = findViewById(R.id.jobDetailsTitle);
        jobDesc = findViewById(R.id.jobDetails);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        email = getIntent().getStringExtra("email");
        employerEmail = getIntent().getStringExtra("employerEmail");
        jobLatitude = getIntent().getFloatExtra("latitude",0);
        jobLongitude = getIntent().getFloatExtra("longitude", 0);
        Log.d("employer email", "" + employerEmail);
        setTextView();
        applyButtonSetup();
        setViewOnMaps();
    }

    /**
     * Sets the text views with job title and description.
     */
    public void setTextView(){
        jobTitle.setText(title);
        jobDesc.setText(desc);
    }

    /**
     * Sets up the Apply button click listener.
     * Handles the process of applying for a job, including validation and navigating to the JobApply activity.
     */
    public void applyButtonSetup(){
        apply = findViewById(R.id.jobDetailsApply);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MerchantIDValidator validator = new MerchantIDValidator();
                validator.isMerchantIDConnected(email, new MerchantIDCallBack() {
                    @Override
                    public void merchantIdAvailableResult(boolean isValid, String merchantID) {
                        if(isValid){
                            Intent applyIntent = new Intent(JobDetailActivity.this, JobApply.class);
                            applyIntent.putExtra("email", email);
                            applyIntent.putExtra("merchantID",merchantID);
                            applyIntent.putExtra("title",title);
                            applyIntent.putExtra("employerEmail",employerEmail);
                            Log.d("JobAdapter", "Email received: " + email);
                            JobDetailActivity.this.startActivity(applyIntent);
                        } else {
                            Intent applyIntent = new Intent(JobDetailActivity.this, Dashboard_User_MyPayPal.class);
                            applyIntent.putExtra("email", email);
                            Toast.makeText(JobDetailActivity.this, "Add your Merchant ID first", Toast.LENGTH_SHORT).show();
                            JobDetailActivity.this.startActivity(applyIntent);
                        }
                    }
                });

            }
        });
    }

    public void setViewOnMaps(){
        viewOnMaps = findViewById(R.id.mapsButton);
        viewOnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this, MapsActivity.class);
                intent.putExtra("latitude",jobLatitude);
                intent.putExtra("longitude",jobLongitude);
                intent.putExtra("title",title);
                JobDetailActivity.this.startActivity(intent);
            }
        });
    }
}