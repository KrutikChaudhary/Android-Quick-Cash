package com.example.group12.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group12.firebase.crud.FirebaseReadManager;
import com.example.group12.ui.employer.Dashboard_Employer;
import com.example.group12.ui.user.Dashboard_User;
import com.example.group12.util.EmailCallback;
import com.example.group12.util.LoginCallback;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.logic.validator.LoginValidator;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity class for user login.
 */
public class LogInActivity extends AppCompatActivity {

    EditText emailTV;
    EditText password;
    Button loginButton;
    FirebaseReadManager dbManager;
    LoginValidator validator;
    private SharedPreferences preferences;
    String userKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseInit();
        setContentView(R.layout.activity_log_in);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        retriveSaved();
        uiSetUp();
        validator = new LoginValidator();
    }

    /**
     * Set up UI components.
     */
    public void uiSetUp(){
        emailTV = findViewById(R.id.email);
        if (userKey != null){
            dbManager.getUserEmail(userKey, new EmailCallback() {
                @Override
                public void onCallback(String email) {
                    emailTV.setText(email);
                }
            });
        }
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_text = emailTV.getText().toString();
                String password_text = password.getText().toString();
                validator.isValidLogin(email_text, password_text, new LoginCallback() {
                    @Override
                    public void onLoginResult(boolean isValid, String role, String key) {
                        Intent intent;
                        if (isValid){
                            Toast.makeText(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            updateSavedUserInfo(key, email_text);
                            if (role.equals("Employee")){
                                intent = new Intent(LogInActivity.this, Dashboard_User.class);
                            }
                            else{
                                intent = new Intent(LogInActivity.this, Dashboard_Employer.class);
                                intent.putExtra("key", key);
                                intent.putExtra("email", email_text);
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

    /**
     * Initialize Firebase database.
     */
    protected void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseReadManager(db);
    }

    private void retriveSaved(){
        userKey = preferences.getString("key", null);
    }

    /**
     * Updates the saved user information locally using SharedPreferences.
     * @param key   The key of the user to be saved.
     * @param email The email of the user to be saved.
     */
    private void updateSavedUserInfo(String key, String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", key);
        editor.putString("email", email);
        editor.apply();
    }

}