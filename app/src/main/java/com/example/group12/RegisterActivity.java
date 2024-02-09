package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupButtonSetup();
    }

    protected void signupButtonSetup(){
        Button signupButton = findViewById(R.id.signUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    protected void databaseInit(){

    }

    protected String getEmail(){
        EditText emailBox = findViewById(R.id.enterEmailText);
        return emailBox.getText().toString().trim();
    }

    protected String getPassword(){
        EditText passwordBox = findViewById(R.id.enterPasswordText);
        return passwordBox.getText().toString().trim();
    }

    protected String getConfirmedPassword(){
        EditText confirmBox = findViewById(R.id.confirmPasswordText);
        return confirmBox.getText().toString().trim();
    }
}