package com.example.group12.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.example.group12.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

/**
 * Activity class for displaying the user profile in the user dashboard.
 */
public class Dashboard_User_View_Stats extends AppCompatActivity {
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_view_stats);
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
}