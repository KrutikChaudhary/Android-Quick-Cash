package com.example.group12.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

/**
 * Activity class for displaying the user profile in the user dashboard.
 */
public class Dashboard_User_View_Stats extends AppCompatActivity {
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