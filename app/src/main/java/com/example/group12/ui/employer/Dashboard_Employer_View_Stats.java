package com.example.group12.ui.employer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.example.group12.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

/**
 * Activity class for displaying the employer profile in the user dashboard.
 */
public class Dashboard_Employer_View_Stats extends AppCompatActivity {
    PieChart pieChart;

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