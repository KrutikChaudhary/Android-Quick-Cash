package com.example.group12.ui.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.group12.R;
import com.example.group12.core.Constants;
import com.example.group12.util.FirebaseCountCallback;
import com.example.group12.util.TotalApplicationsStatsCount;
import com.example.group12.util.TotalJobsStatsCount;
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
    final int INITIAL_APPLICATION = 0;
    final int INITIAL_JOBS = 0;

    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_employer_view_stats);
        pieChart = findViewById(R.id.chart);
        initializeDatabase();
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        employerEmail = preferences.getString("email", "");

        // Retrieve TextViews representing job and application counts
        TextView textViewGreenCount = findViewById(R.id.textViewGreenCount);
        TextView textViewYellowCount = findViewById(R.id.textViewYellowCount);
        TotalJobsStatsCount totalJobsStatsCount = new TotalJobsStatsCount();
        totalJobsStatsCount.getTotalCounts(employerEmail, new FirebaseCountCallback() {
            @Override
            public void dataCount(int countOfJobs) {
                Log.d("Job Count", "Count: "+ countOfJobs);
                updatePieChart(countOfJobs, INITIAL_APPLICATION); // Initial call with 0 applications
                textViewGreenCount.setText(String.valueOf(countOfJobs)); // Set job count
            }
        });
        TotalApplicationsStatsCount totalApplicationsStatsCount = new TotalApplicationsStatsCount();
        totalApplicationsStatsCount.getTotalCounts(employerEmail, new FirebaseCountCallback() {
            @Override
            public void dataCount(int countOfApplications) {
                Log.d("Job Application", "Count: " + countOfApplications);
                updatePieChart(INITIAL_JOBS, countOfApplications); // Update only the applications part
                textViewYellowCount.setText(String.valueOf(countOfApplications)); // Set application count
            }
        });

    }

    /**
     * Updates the pie chart with the given number of jobs and applications.
     * If the number of jobs is greater than 0, a pie slice representing "My Jobs" is added to the chart.
     * If the number of applications is greater than 0, a pie slice representing "Job Applications" is added to the chart.
     * The chart is then animated.
     * @param jobs         The number of jobs to be represented on the pie chart.
     * @param applications The number of job applications to be represented on the pie chart.
     */
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