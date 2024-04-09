package com.example.group12.util.statsCount.employer;

import androidx.annotation.NonNull;

import com.example.group12.core.Constants;
import com.example.group12.util.callback.FirebaseCountCallback;
import com.example.group12.util.statsCount.StatsCountStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * This class implements the StatsCountStrategy interface to calculate the total number of jobs posted
 * received by a specific employer identified by their email.
 */
public class TotalJobsStatsCount implements StatsCountStrategy {
    FirebaseDatabase db;

    public TotalJobsStatsCount() {
        initializeDatabase();
    }

    /**
     * Retrieves the total number of jobs posted by a specific employer identified by their email.
     * Invokes the provided callback interface with the total count of jobs.
     * @param email The email of the employer whose jobs are to be counted.
     * @param callback      The callback interface to handle the total count of jobs.
     */
    @Override
    public void getTotalCounts(String email, FirebaseCountCallback callback) {
        DatabaseReference jobRef = db.getReference().child("Job");
        jobRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total jobs posted by employer.
                for (DataSnapshot jobs: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobs.getValue();
                    String emailData = (String) userCredentials.get("employerEmail");
                    if(email.equals(emailData)){ //if the employer email matches then increment
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

    private void initializeDatabase(){
        this.db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
    }
}
