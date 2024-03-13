package com.example.group12.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.logic.UserCredentialValidator;
import com.google.firebase.database.FirebaseDatabase;

import com.example.group12.Firebase.FirebaseDatabaseManager;


public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupButtonSetup();
        databaseInit();
    }

    protected void signupButtonSetup(){
        Button signupButton = findViewById(R.id.signUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanupLabels();
                UserCredentialValidator userCredentialValidator = new UserCredentialValidator();
                boolean validated = false;
                String emailMessage = "";
                String passwordMessage = "";

                if (userCredentialValidator.isEmailEmpty(getEmail())){
                    emailMessage = Constants.EMAIL_EMPTY;
                }
                else{
                    if (userCredentialValidator.isValidEmailAddress(getEmail())){
                        if (!userCredentialValidator.isPasswordEmpty(getPassword())){
                            if (userCredentialValidator.isPasswordMatch(getPassword(), getConfirmedPassword())){
                                if (userCredentialValidator.checkPasswordLength(getPassword())){
                                    if (userCredentialValidator.isPasswordValid(getPassword())){
                                        validated = true;
                                    }
                                    else{
                                        passwordMessage = Constants.PASSWORD_INVALID;
                                    }
                                }
                                else{
                                    passwordMessage = Constants.PASSWORD_INVALID_LENGTH;
                                }
                            }
                            else{
                                passwordMessage = Constants.PASSWORD_INVALID_LENGTH;
                            }
                        }
                        else{
                            passwordMessage = Constants.PASSWORD_EMPTY;
                        }
                    }
                    //if email is not valid
                    else{
                        emailMessage = Constants.EMAIL_INVALID;
                    }
                }

                if (validated){
                    saveToFirebase();
                    Intent selectRoleIntent = new Intent(RegisterActivity.this, SelectRoleActivity.class);
                    selectRoleIntent.putExtra("key", dbManager.getUserRef().getKey());
                    RegisterActivity.this.startActivity(selectRoleIntent);
                }
                else{
                    if (!emailMessage.isEmpty()){
                        setEmailLabel(emailMessage);
                    }
                    else{
                        setPasswordLabel(passwordMessage);
                    }
                }
            }
        });
    }

    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }

    protected void saveToFirebase(){
        dbManager.saveUserCredentialsToFirebase(getEmail(), getPassword());
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

    protected void setEmailLabel(String message){
        TextView emailLabel = findViewById(R.id.emailLabel);
        emailLabel.setText(message.trim());
    }

    protected void setPasswordLabel(String message){
        TextView passwordLabel = findViewById(R.id.passwordLabel);
        passwordLabel.setText(message.trim());
    }

    protected void cleanupLabels(){
        setEmailLabel("");
        setPasswordLabel("");
    }
}