package com.example.group12.ui.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.example.group12.R;
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
 * Activity class for displaying the employer profile in the user dashboard.
 */
public class Dashboard_Employer_View_Stats extends AppCompatActivity {
    PieChart pieChart;
    FirebaseDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_view_stats);
        pieChart = findViewById(R.id.chart);

        pieChart.addPieSlice(
                new PieModel(
                        "R",
                        17,
                        Color.parseColor("#FFA726")));

        pieChart.addPieSlice(
                new PieModel(
                        "Python",
                        6,
                        Color.parseColor("#66BB6A")));
        pieChart.startAnimation();
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

    private void updatePieChart(int jobs, int applications) {
        if (jobs > 0) {
            pieChart.addPieSlice(new PieModel("My Jobs", jobs, Color.parseColor("#008000"))); //yellow
        }
        if (applications > 0) {
            pieChart.addPieSlice(new PieModel("Job Applications", applications, Color.parseColor("#FFA726"))); //grey
        }
        pieChart.startAnimation();
    }
}