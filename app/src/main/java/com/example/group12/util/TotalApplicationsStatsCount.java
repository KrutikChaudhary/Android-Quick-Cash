package com.example.group12.util;

import androidx.annotation.NonNull;

import com.example.group12.core.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class TotalApplicationsStatsCount implements StatsCountStrategy{
    FirebaseDatabase db;
    public TotalApplicationsStatsCount(){
        initializeDatabase();
    }
    /**
     * Retrieves the total number of job applications received by a specific employer identified by their email.
     * Invokes the provided callback interface with the total count of job applications.
     * @param email The email of the employer whose job applications are to be counted.
     * @param callback      The callback interface to handle the total count of job applications.
     */
    @Override
    public void getTotalCounts(String email, FirebaseCountCallback callback) {
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter = 0;
                //iterate the data and find count the total job applications posted by employer.
                for (DataSnapshot jobApplications: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobApplications.getValue();
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
