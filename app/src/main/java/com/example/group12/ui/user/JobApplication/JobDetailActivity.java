package com.example.group12.ui.user.JobApplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group12.ui.user.Dashboard_User_MyPayPal;
import com.example.group12.R;
import com.example.group12.util.MerchantIDCallBack;
import com.example.group12.logic.MerchantIDValidator;

public class JobDetailActivity extends AppCompatActivity {
    String title;
    String desc;
    String email;
    TextView jobTitle;
    TextView jobDesc;
    Button apply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        jobTitle = findViewById(R.id.jobDetailsTitle);
        jobDesc = findViewById(R.id.jobDetails);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        email = getIntent().getStringExtra("email");
        setTextView();
        applyButtonSetup();
    }
    public void setTextView(){
        jobTitle.setText(title);
        jobDesc.setText(desc);
    }

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
}
