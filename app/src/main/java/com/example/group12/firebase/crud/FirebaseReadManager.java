package com.example.group12.firebase.crud;

import androidx.annotation.NonNull;

import com.example.group12.logic.FilterJob;
import com.example.group12.model.Job;
import com.example.group12.util.EmailCallback;
import com.example.group12.util.JobFilterCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseReadManager {
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference merchantRef;
    private DatabaseReference jobRef;

    public FirebaseReadManager(FirebaseDatabase database) {
        this.database = database;
        initializeDatabaseRefs();
    }

    /**
     * Initializes database references.
     */
    protected void initializeDatabaseRefs() {
        userRef = database.getReference().child("User");
        jobRef = database.getReference().child("Job");
        merchantRef = database.getReference().child("MerchantID");

    }

    public void getUserEmail(String key, EmailCallback callback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();
                String email = null;
                if (user != null) {
                    email = (String) user.get("Email");
                }
                callback.onCallback(email); // Invoke the callback with the email value
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }

    /**
     * Filters jobs based on specified parameters.
     * @param parameter Parameter to filter jobs
     * @param salary Salary range to filter jobs
     * @param duration Duration range to filter jobs
     * @param distance Distance range to filter jobs
     * @param callback Callback to handle filtered jobs
     */
    public void jobFilter(String parameter, String salary, String duration, String distance, JobFilterCallback callback){
        // Create a list to store filtered jobs
        List<Job> filterdJobList = new ArrayList<>();
        // Retrieve DatabaseReference for Job node and listen for a single value event
        this.getJobRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Iterate through each child node under the Job node
                for (DataSnapshot jobSnapshot : snapshot.getChildren()){
                    // Extract job details from the DataSnapshot
                    Map<String, Object> jobMap = (Map<String, Object>) jobSnapshot.getValue();
                    String employerEmail = (String) jobMap.get("employerEmail");
                    String jobTitle = (String) jobMap.get("title");
                    float jobSalary = ((Number) jobMap.get("salary")).floatValue();
                    int jobDuration = ((Number) jobMap.get("duration")).intValue();
                    String jobStartDate = (String) jobMap.get("startDate");
                    String jobUrgency = (String) jobMap.get("urgency");
                    String jobLocation = (String) jobMap.get("Location");
                    float jobLongitude = ((Number) jobMap.get("longitude")).floatValue();
                    float jobLatitude = ((Number) jobMap.get("latitude")).floatValue();

                    // Create a new instance of FilterJob to perform filtering
                    FilterJob filterJob = new FilterJob();

                    // Check if the job satisfies the filtering criteria
                    if (filterJob.containsParameters(parameter, jobTitle) && filterJob.containsSalary(salary, jobSalary) && filterJob.containsDuration(duration, jobDuration)){
                        // If the job meets the criteria, create a Job object and add it to the filtered job list
                        Job job = new Job(jobTitle,employerEmail, jobSalary, jobDuration, jobStartDate, jobLocation, jobUrgency, jobLatitude, jobLongitude);
                        filterdJobList.add(job);
                    }

                    // Pass the filtered job list to the callback function
                    callback.onJobFilterSuccess(filterdJobList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if any
            }
        });
    }

    /**
     * Retrieves DatabaseReference for User node.
     * @return DatabaseReference for User node
     */
    public DatabaseReference getUserRef(){
        return this.userRef;
    }

    /**
     * Retrieves DatabaseReference for MerchantID node.
     * @return DatabaseReference for MerchantID node
     */
    public DatabaseReference getMerchantRef(){
        return this.merchantRef;
    }

    /**
     * Retrieves DatabaseReference for Job node.
     * @return DatabaseReference for Job node
     */
    public DatabaseReference getJobRef(){
        return this.jobRef;
    }
}
