package com.example.group12.util.statsCount.employee;

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
 * This class implements the StatsCountStrategy interface to calculate the total number of in-review:q job applications
 * received by a specific employee identified by their email.
 */
public class InReviewApplicationCount implements StatsCountStrategy {
    FirebaseDatabase db;

    public InReviewApplicationCount() {
        initializeDatabase();
    }

    /**
     * Retrieves the count of job applications for a specific employee with a particular status.
     *
     * @param email The email of the employee whose job applications are to be counted.
     * @param callback The callback interface to handle the count of InReview job applications.
     */
    @Override
    public void getTotalCounts(String email, FirebaseCountCallback callback) {
        DatabaseReference jobApplicationRef = db.getReference().child("Job Application");
        jobApplicationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int counter =0;
                //iterate the data and find count the total in review jobs.
                for (DataSnapshot jobApplication: snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) jobApplication.getValue();
                    String emailData = (String) userCredentials.get("Email");
                    String applicationStatus = (String) userCredentials.get("applicationStatus");
                    if(email.equals(emailData)){
                        if(applicationStatus.equals("InReview")){
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

    private void initializeDatabase(){
        this.db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
    }
}
