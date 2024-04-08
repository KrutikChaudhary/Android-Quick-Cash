package com.example.group12.ui.user;
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
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_view_stats);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        pieChart = findViewById(R.id.chart);
        email = preferences.getString("email", "");
        initializeDatabase();
        // Retrieve TextViews representing job and application counts
        TextView textViewGreenCount = findViewById(R.id.textViewGreenCount); // accepted jobs
        TextView textViewYellowCount = findViewById(R.id.textViewYellowCount); // in review jobs
        TextView textViewRedCount = findViewById(R.id.textViewRedCount); // rejected jobs
        getApplicationCount(email,"InReview", new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job In Review Count", "In Review Count: "+ count);
                updatePieChart(count,JOBS_ACCEPTED,JOBS_REJECTED);
                textViewYellowCount.setText(String.valueOf(count));
            }
        });
        getApplicationCount(email,"Accepted", new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job Accepted Count", "Accepted Count: "+ count);
                updatePieChart(JOBS_IN_REVIEW, count,JOBS_REJECTED);
                textViewGreenCount.setText(String.valueOf(count));
            }
        });
        getApplicationCount(email,"Rejected", new FirebaseCountCallback() {
            @Override
            public void dataCount(int count) {
                Log.d("Job Rejected Count", "Rejected Count: "+ count);
                updatePieChart(JOBS_IN_REVIEW, JOBS_ACCEPTED,count);
                textViewRedCount.setText(String.valueOf(count));
            }
        });
    }
    /**
     * Retrieves the count of job applications for a specific employee with a particular status.
     *
     * @param employeeEmail The email of the employee whose job applications are to be counted.
     * @param statusToCheck The status of the job applications to be counted.
     * @param callback The callback interface to handle the count of job applications.
     */
    public void getApplicationCount(String employeeEmail, String statusToCheck, FirebaseCountCallback callback){
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
                        if(applicationStatus.equals(statusToCheck)){
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
    /**
     * Updates the pie chart with the given number of jobs in review, accepted, and rejected.
     * If the number of jobs in review is greater than 0, a pie slice representing "Jobs in Review" is added to the chart.
     * If the number of accepted jobs is greater than 0, a pie slice representing "Jobs Accepted" is added to the chart.
     * If the number of rejected jobs is greater than 0, a pie slice representing "Jobs Rejected" is added to the chart.
     * The chart is then animated.
     * @param jobsInReview The number of jobs currently in review.
     * @param jobsAccepted The number of jobs accepted.
     * @param jobsRejected The number of jobs rejected.
     */
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