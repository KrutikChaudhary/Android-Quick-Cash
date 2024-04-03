package com.example.group12.ui.user.JobApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.ui.user.Dashboard_User;
import com.example.group12.ui.user.Dashboard_User_MyPayPal;
import com.example.group12.util.MerchantIDCallBack;
import com.example.group12.logic.MerchantIDValidator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity class for applying to a job.
 * This activity allows users to apply for a job by entering their full name and, if required, their merchant ID.
 */
public class JobApply extends AppCompatActivity {
    EditText fullName;
    Button applyJob;
    String email;
    String merchantID;
    TextView jobTitle;
    String employerEmail;
    String title;
    FirebaseDatabaseManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);
        fullName = findViewById(R.id.applyEnterFullName);
        jobTitle = findViewById(R.id.textViewJobTitle);
        email = getIntent().getStringExtra("email");
        merchantID = getIntent().getStringExtra("merchantID");
        employerEmail = getIntent().getStringExtra("employerEmail");
        title = getIntent().getStringExtra("title");
        jobTitle.setText(title);
        applyJobButtonSetup();
    }

    /**
     * Sets up the Apply Job button click listener.
     * Handles the process of applying for a job, including validation and saving the application to Firebase.
     */
    public void applyJobButtonSetup(){
        applyJob=findViewById(R.id.applyToJob);
        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString();
                MerchantIDValidator validator = new MerchantIDValidator();
                validator.isMerchantIDConnected(email, new MerchantIDCallBack() {
                    @Override
                    public void merchantIdAvailableResult(boolean isValid, String merchantID) {
                        if(isValid){
                            if(name!=null&&!name.equals("")){
                                saveJobApplicationToFirebase(email,employerEmail,title, name, merchantID);
                                Intent intent = new Intent(JobApply.this, Dashboard_User.class);
                                intent.putExtra("email",email);
                                Log.d("JobAdapter", "Email received: " + email);
                                Toast.makeText(JobApply.this, "Application Successful", Toast.LENGTH_SHORT).show();
                                JobApply.this.startActivity(intent);
                            } else {
                                Toast.makeText(JobApply.this, "Please add your Full name", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent applyIntent = new Intent(JobApply.this, Dashboard_User_MyPayPal.class);
                            applyIntent.putExtra("email", email);
                            Toast.makeText(JobApply.this, "Add your Merchant ID first", Toast.LENGTH_SHORT).show();
                            JobApply.this.startActivity(applyIntent);
                        }
                    }
                });
            }
        });
    }


    public DatabaseReference saveJobApplicationToFirebase(String employeeEmail, String employerEmail, String jobTitle, String employeeName, String employeeMerchantID){
        dbManager = new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
        DatabaseReference ref =  dbManager.saveJobApplicationToFirebase(employeeEmail,employerEmail,jobTitle,employeeName,employeeMerchantID);
        return ref;
    }
}