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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.group12.Firebase.FirebaseDatabaseManager;

/**
 * Activity class for user registration.
 */
public class RegisterActivity extends AppCompatActivity {

    FirebaseDatabaseManager dbManager;
    DatabaseReference dbref;

    /**
     * Initializes the activity layout and sets up the signup button.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        signupButtonSetup();
        databaseInit();
    }

    /**
     * Sets up the signup button click listener.
     * Validates user input and saves user credentials to Firebase if validated.
     * Redirects to SelectRoleActivity upon successful registration.
     */
    public void signupButtonSetup(){
        Button signupButton = findViewById(R.id.signUpButton);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanupLabels();
                UserCredentialValidator userCredentialValidator = new UserCredentialValidator();
                boolean validated = false;
                String emailMessage = "";
                String passwordMessage = "";

                // Validate email and password
                // Email validation
                if (userCredentialValidator.isEmailEmpty(getEmail())){
                    emailMessage = Constants.EMAIL_EMPTY;
                }
                else{
                    if (userCredentialValidator.isValidEmailAddress(getEmail())){
                        // Password validation
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

                // If credentials are validated, save to Firebase and redirect to SelectRoleActivity
                if (validated){
                    saveToFirebase();
                    Intent selectRoleIntent = new Intent(RegisterActivity.this, SelectRoleActivity.class);
                    selectRoleIntent.putExtra("email",getEmail());
                    selectRoleIntent.putExtra("key", dbref.getKey());
                    RegisterActivity.this.startActivity(selectRoleIntent);
                }
                // Display error messages if validation fails
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

    /**
     * Initializes the Firebase database.
     */
    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }

    /**
     * Saves user credentials (email and password) to Firebase.
     */
    protected void saveToFirebase(){
        dbref = dbManager.saveUserCredentialsToFirebase(getEmail(), getPassword());
    }

    /**
     * Retrieves the email entered by the user.
     *
     * @return The email entered by the user.
     */
    protected String getEmail(){
        EditText emailBox = findViewById(R.id.enterEmailText);
        return emailBox.getText().toString().trim();
    }
    /**
     * Retrieves the password entered by the user.
     *
     * @return The password entered by the user.
     */
    protected String getPassword(){
        EditText passwordBox = findViewById(R.id.enterPasswordText);
        return passwordBox.getText().toString().trim();
    }

    /**
     * Retrieves the confirmed password entered by the user.
     *
     * @return The confirmed password entered by the user.
     */
    protected String getConfirmedPassword(){
        EditText confirmBox = findViewById(R.id.confirmPasswordText);
        return confirmBox.getText().toString().trim();
    }

    /**
     * Sets the email label with the provided error message.
     *
     * @param message The error message to be displayed.
     */
    protected void setEmailLabel(String message){
        TextView emailLabel = findViewById(R.id.emailLabel);
        emailLabel.setText(message.trim());
    }

    /**
     * Sets the password label with the provided error message.
     *
     * @param message The error message to be displayed.
     */
    protected void setPasswordLabel(String message){
        TextView passwordLabel = findViewById(R.id.passwordLabel);
        passwordLabel.setText(message.trim());
    }

    /**
     * Clears the error messages displayed in the email and password labels.
     */
    protected void cleanupLabels(){
        setEmailLabel("");
        setPasswordLabel("");
    }
}