package com.example.group12.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.logic.LoginValidator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    FirebaseDatabaseManager dbManager;

    LoginValidator validator;
    Map<String, Object> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseInit();
        setContentView(R.layout.activity_log_in);
        uiSetUp();
        validator = new LoginValidator();
    }

    public void uiSetUp(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                validator.isValidLogin(email_text,password_text);
                if (validator.valid){
                    Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    if (validator.role.equals("Employee")){
                        intent = new Intent(LogInActivity.this, Dashboard_User.class);
                    }
                    else{
                        intent = new Intent(LogInActivity.this, Dashboard_Employer.class);
                    }

                    LogInActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }



}