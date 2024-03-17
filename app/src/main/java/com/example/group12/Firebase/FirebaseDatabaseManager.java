package com.example.group12.Firebase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.LocationInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/*
DatabaseConnection.java
AUTHOR: Chaz Davies

This file includes a class with all methods related to the database.
 */

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.example.group12.logic.FilterJob;
import com.example.group12.model.Job;
import com.example.group12.util.JobFilterCallback;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.group12.core.Constants;

/*
DatabaseConnection.java
AUTHOR: Chaz Davies
This file includes a class with all methods related to the database.
*/

public class FirebaseDatabaseManager
{
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference userLocation;
    private DatabaseReference jobRef;

    public FirebaseDatabaseManager(){}
    public FirebaseDatabaseManager(FirebaseDatabase database) {
        this.database = database;
//this.setDBListener();
        this.initializeDatabaseRefs();
    }
// protected void setDBListener() {
// this.db_ref.addValueEventListener(new ValueEventListener() {
// @Override
// public void onDataChange(@NonNull DataSnapshot snapshot) {
// //incomplete method, add your implementation
// extractedMessage = snapshot.getValue(String.class);
// }
//
// @Override
// public void onCancelled(@NonNull DatabaseError error) {
//
// }
// });
// }

    protected void initializeDatabaseRefs() {
        userRef = database.getReference().child("User").push();
        userLocation = database.getReference().child("Location").push();
    }
    public DatabaseReference saveUserCredentialsToFirebase(String email, String password){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Password", password);
        DatabaseReference dbref = this.userRef.push();
        dbref.setValue(map);
        return dbref;
    }

    /*
     * This method saves the location details to firebase
     *
     * @param locationInfo Location info objects which stores the location
     * @return void
     */
    public void saveLocationToFirebase(LocationInfo locationInfo){
        Map<String, Object> locationMap = new HashMap<>();
        locationMap.put("latitude",  locationInfo.getLatitude());
        locationMap.put("longitude", locationInfo.getLongitude());
        locationMap.put("address", locationInfo.getAddress());
        locationMap.put("locality", locationInfo.getLocality());
        locationMap.put("countryCode", locationInfo.getCountryCode());

        userLocation.setValue(locationMap);
    }
    public void updateRole(String role, String userKey){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(userKey);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();
                if (user != null){
                    user.put("Role", role);
                    ref.setValue(user);
                }
                else{
                    Log.e("null user", "User map is null");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    public DatabaseReference getUserRef(){
        return this.userRef;
    }

    public DatabaseReference getJobRef(){
        return this.jobRef;
    }
    public void jobFilter(String parameter, String salary, String duration, String distance, JobFilterCallback callback){
        List<Job> filterdJobList = new ArrayList<>();
        this.getJobRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot jobSnapshot : snapshot.getChildren()){
                    Map<String, Object> jobMap = (Map<String, Object>) jobSnapshot.getValue();
                    String jobTitle = (String) jobMap.get("title");
                    float jobSalary = (float) jobMap.get("salary");
                    int jobDuration = (int) jobMap.get("duration");
                    String jobStartDate = (String) jobMap.get("startDate");
                    String jobUrgency = (String) jobMap.get("urgency");
                    String jobLocation = (String) jobMap.get("Location");
                    float jobLongitude = (float) jobMap.get("longitude");
                    float jobLatitude = (float) jobMap.get("longitude");

                    // getUser location


                    FilterJob filterJob = new FilterJob();



                    if (filterJob.containsParameters(parameter, jobTitle) && filterJob.containsSalary(salary, jobSalary) && filterJob.containsDuration(duration, jobDuration)){
                        Job job = new Job(jobTitle, jobSalary, jobDuration, jobStartDate, jobLocation, jobUrgency, jobLatitude, jobLongitude);
                        filterdJobList.add(job);
                    }

                    callback.onJobFilterSuccess(filterdJobList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
