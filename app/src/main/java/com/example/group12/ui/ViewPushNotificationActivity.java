package com.example.group12.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.R;

public class ViewPushNotificationActivity extends AppCompatActivity {

    private TextView title;
    private TextView jobTitle;
    private TextView jobSalary;
    private TextView jobLocation;
    private Button loginButton;
    private Bundle jobData;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notification);
        init();
        setText();
        buttonSetup();
    }

    /**
     * Initializes views and retrieves data from the intent extras.
     * This method should be called in the onCreate() method or at the beginning of the activity lifecycle.
     */
    protected void init() {
        title = findViewById(R.id.notification_title_textview);
        jobTitle = findViewById(R.id.notification_jobTitle_textview);
        jobSalary = findViewById(R.id.notification_salary_textview);
        jobLocation = findViewById(R.id.notification_location_textview);
        loginButton = findViewById(R.id.notification_login_button);
        jobData = getIntent().getExtras();
    }

    /**
     * Sets text for views using data retrieved from intent extras.
     * This method should be called after initializing views and retrieving data from intent extras.
     */
    protected void setText(){
        title.setText(jobData.getString("title"));
        jobTitle.setText(jobData.getString("jobTitle"));
        jobSalary.setText(jobData.getString("jobSalary"));
        jobLocation.setText(jobData.getString("jobLocation"));
    }

    /**
     * Sets up the functionality for the login button.
     * When the button is clicked, it navigates to the login activity.
     * This method should be called after initializing the login button.
     */
    protected void buttonSetup(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPushNotificationActivity.this, LogInActivity.class);
                ViewPushNotificationActivity.this.startActivity(intent);
            }
        });
    }

}
