package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.ui.Dashboard_User;
import com.example.group12.ui.LogInActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JobApply extends AppCompatActivity {
    EditText fullName;
    Button applyJob;
    String email;
    String merchantID;
    TextView emailView;
    FirebaseDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);
        fullName = findViewById(R.id.applyEnterFullName);
        emailView = findViewById(R.id.TempEmail);
        email = getIntent().getStringExtra("email");
        merchantID = getIntent().getStringExtra("merchantID");
        emailView.setText(email);
        applyJobButtonSetup();
    }

    public void applyJobButtonSetup(){
        applyJob=findViewById(R.id.applyToJob);
        applyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = fullName.getText().toString();
                if(name!=null&&!name.equals("")){
                    saveJobApplicationToFirebase(email, name, merchantID);
                    Intent intent = new Intent(JobApply.this, Dashboard_User.class);
                    intent.putExtra("email",email);
                    Toast.makeText(JobApply.this, "Application Successful", Toast.LENGTH_SHORT).show();
                    JobApply.this.startActivity(intent);
                } else {
                    Toast.makeText(JobApply.this, "Please add your Full name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public DatabaseReference saveJobApplicationToFirebase(String email, String fullname, String merchantID){
        dbManager = new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
        DatabaseReference ref =  dbManager.saveJobApplicationToFirebase(email,fullname,merchantID);
        return ref;
    }



}