package com.example.group12.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.util.LoginCallback;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.logic.LoginValidator;
import com.google.firebase.database.FirebaseDatabase;

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

                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                validator.isValidLogin(email_text, password_text, new LoginCallback() {
                    @Override
                    public void onLoginResult(boolean isValid, String role) {
                        Intent intent;
                        if (isValid){
                            Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            if (role.equals("Employee")){
                                intent = new Intent(LogInActivity.this, Dashboard_User.class);
                                intent.putExtra("email", email_text);
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
        });
    }
    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }



}