package com.example.group12.ui.user.jobApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group12.firebase.crud.FirebaseCreateManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.ui.user.Dashboard_User;
import com.example.group12.ui.user.Dashboard_User_MyPayPal;
import com.example.group12.util.MerchantIDCallBack;
import com.example.group12.logic.validator.MerchantIDValidator;
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
    FirebaseCreateManager dbManager;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        fullName = findViewById(R.id.applyEnterFullName);
        jobTitle = findViewById(R.id.textViewJobTitle);
        email = preferences.getString("email", "");
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

    /**
     * Saves a job application to Firebase.
     * @param employeeEmail      The email of the employee applying for the job.
     * @param employerEmail      The email of the employer to whom the job application is being sent.
     * @param jobTitle           The title of the job being applied for.
     * @param employeeName       The name of the employee applying for the job.
     * @param employeeMerchantID The merchant ID of the employee applying for the job.
     * @return A DatabaseReference object pointing to the location of the saved job application in the Firebase Database.
     */
    public DatabaseReference saveJobApplicationToFirebase(String employeeEmail, String employerEmail, String jobTitle, String employeeName, String employeeMerchantID){
        dbManager = new FirebaseCreateManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
        DatabaseReference ref =  dbManager.saveJobApplicationToFirebase(employeeEmail,employerEmail,jobTitle,employeeName,employeeMerchantID);
        return ref;
    }
}