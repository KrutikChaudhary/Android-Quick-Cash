package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference userRef;

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
                Validator validator = new Validator();
                boolean validated = false;
                String emailMessage = "";
                String passwordMessage = "";

                if (validator.isEmailEmpty(getEmail())){
                    emailMessage = getResources().getString(R.string.EMAIL_EMPTY).trim();
                }
                else{
                    if (validator.isValidEmailAddress(getEmail())){
                        if (!validator.isPasswordEmpty(getPassword())){
                            if (validator.isPasswordMatch(getPassword(), getConfirmedPassword())){
                                if (validator.checkPasswordLength(getPassword())){
                                    if (validator.isPasswordValid(getPassword())){
                                        validated = true;
                                    }
                                    else{
                                        passwordMessage = getResources().getString(R.string.PASSWORD_INVALID).trim();
                                    }
                                }
                                else{
                                    passwordMessage = getResources().getString(R.string.PASSWORD_INVALID_LENGTH).trim();
                                }
                            }
                            else{
                                passwordMessage = getResources().getString(R.string.PASSWORD_MISMATCH);
                            }
                        }
                        else{
                            passwordMessage = getResources().getString(R.string.PASSWORD_EMPTY);
                        }
                    }
                    //if email is not valid
                    else{
                        emailMessage = getResources().getString(R.string.EMAIL_INVALID).trim();
                    }
                }

                if (validated){
                    saveToFirebase();
                    Intent selectRoleIntent = new Intent(RegisterActivity.this, SelectRoleActivity.class);
                    selectRoleIntent.putExtra("key", userRef.getKey());
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
        db = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_LINK));
    }

    protected void saveToFirebase(){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", getEmail());
        map.put("Password", getPassword());

        userRef = db.getReference().child("User").push();
        userRef.setValue(map);

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