package com.example.group12.ui.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.util.FirebaseCountCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Map;

/**
 * Activity class for displaying the user profile in the user dashboard.
 */
public class Dashboard_User_View_Stats extends AppCompatActivity {
    public static final int JOBS_REJECTED = 0;
    public static final int JOBS_ACCEPTED = 0;
    public static final int JOBS_IN_REVIEW = 0;
    PieChart pieChart;
    FirebaseDatabase db;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_view_stats);
        pieChart = findViewById(R.id.chart);
        email = getIntent().getStringExtra("email");
        initializeDatabase();

        // Retrieve TextViews representing job and application counts
        TextView textViewGreenCount = findViewById(R.id.textViewGreenCount); // accepted jobs
        TextView textViewYellowCount = findViewById(R.id.textViewYellowCount); // in review jobs
        TextView textViewRedCount = findViewById(R.id.textViewRedCount); // rejected jobs

        getTotalInReviewJobs(email, new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job In Review Count", "In Review Count: "+ count);
                updatePieChart(count,JOBS_ACCEPTED,JOBS_REJECTED);
                textViewYellowCount.setText(String.valueOf(count));
            }
        });

        getTotalAcceptedJobs(email, new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job Accepted Count", "Accepted Count: "+ count);
                updatePieChart(JOBS_IN_REVIEW, count,JOBS_REJECTED);
                textViewGreenCount.setText(String.valueOf(count));
            }
        });

        getTotalRejectedJobs(email, new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job Rejected Count", "Rejected Count: "+ count);
                updatePieChart(JOBS_IN_REVIEW, JOBS_ACCEPTED,count);
                textViewRedCount.setText(String.valueOf(count));
            }
        });
    }

    public void getTotalInReviewJobs(String employeeEmail, FirebaseCountCallback callback){
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total in review jobs.
                for (DataSnapshot jobApplication: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobApplication.getValue();
                    String email = (String) userCredentials.get("Email");
                    String applicationStatus = (String) userCredentials.get("applicationStatus");

                    if(employeeEmail.equals(email)){
                        if(applicationStatus.equals("InReview")){
                            counter++;
                        }
                    }
                }

                callback.dataCount(counter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalAcceptedJobs(String employeeEmail, FirebaseCountCallback callback){
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total accepted jobs.
                for (DataSnapshot jobApplication: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobApplication.getValue();
                    String email = (String) userCredentials.get("Email");
                    String applicationStatus = (String) userCredentials.get("applicationStatus");

                    if(employeeEmail.equals(email)){
                        if(applicationStatus.equals("Accepted")){
                            counter++;
                        }
                    }
                }

                callback.dataCount(counter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalRejectedJobs(String employeeEmail, FirebaseCountCallback callback){
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total rejected jobs.
                for (DataSnapshot jobApplication: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobApplication.getValue();
                    String email = (String) userCredentials.get("Email");
                    String applicationStatus = (String) userCredentials.get("applicationStatus");

                    if(employeeEmail.equals(email)){
                        if(applicationStatus.equals("Rejected")){
                            counter++;
                        }
                    }
                }

                callback.dataCount(counter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updatePieChart(int jobsInReview, int jobsAccepted, int jobsRejected) {
        if (jobsInReview > 0) {
            pieChart.addPieSlice(new PieModel("Jobs in Review", jobsInReview, Color.parseColor("#FFA726"))); //yellow
        }
        if (jobsAccepted > 0) {
            pieChart.addPieSlice(new PieModel("Jobs Accepted", jobsAccepted, Color.parseColor("#008000"))); //yellow
        }
        if (jobsRejected > 0) {
            pieChart.addPieSlice(new PieModel("Jobs Rejected", jobsRejected, Color.parseColor("#FF0000"))); //red
        }
        pieChart.startAnimation();
    }

    private void initializeDatabase(){
        this.db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
    }
}