package com.example.group12.ui.employer;

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
 * Activity class for displaying the employer statistics.
 */
public class Dashboard_Employer_View_Stats extends AppCompatActivity {
    PieChart pieChart;
    FirebaseDatabase db;
    String employerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_view_stats);
        pieChart = findViewById(R.id.chart);
        initializeDatabase();
        employerEmail = getIntent().getStringExtra("email");

        // Retrieve TextViews representing job and application counts
        TextView textViewGreenCount = findViewById(R.id.textViewGreenCount);
        TextView textViewYellowCount = findViewById(R.id.textViewYellowCount);

        getTotalJobs(employerEmail, new FirebaseCountCallback() {
            @Override
            public void dataCount(int countOfJobs) {
                Log.d("Job Count", "Count: "+ countOfJobs);
                updatePieChart(countOfJobs, 0); // Initial call with 0 applications
                textViewGreenCount.setText(String.valueOf(countOfJobs)); // Set job count
            }
        });

        getTotalApplications(employerEmail, new FirebaseCountCallback() {
            @Override
            public void dataCount(int countOfApplications) {
                Log.d("Job Application", "Count: " + countOfApplications);
                updatePieChart(0, countOfApplications); // Update only the applications part
                textViewYellowCount.setText(String.valueOf(countOfApplications)); // Set application count
            }
        });

    }

    public void getTotalJobs(String employerEmail, FirebaseCountCallback callback){
        DatabaseReference jobRef = db.getReference().child("Job");
        jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total jobs posted by employer.
                for (DataSnapshot jobs: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobs.getValue();
                    String email = (String) userCredentials.get("employerEmail");
                    if(employerEmail.equals(email)){ //if the employer email matches then increment
                        counter++;
                    }
                }

                callback.dataCount(counter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void getTotalApplications(String employerEmail, FirebaseCountCallback callback){
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                //iterate the data and find count the total job applications posted by employer.
                for (DataSnapshot jobApplications: snapshot.getChildren()){
                    counter ++;
                }
                callback.dataCount(counter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void updatePieChart(int jobs, int applications) {
        if (jobs > 0) {
            pieChart.addPieSlice(new PieModel("My Jobs", jobs, Color.parseColor("#008000"))); //green
        }
        if (applications > 0) {
            pieChart.addPieSlice(new PieModel("Job Applications", applications, Color.parseColor("#FFA726"))); //yellow
        }
        pieChart.startAnimation();
    }

    private void initializeDatabase(){
        this.db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
    }
}