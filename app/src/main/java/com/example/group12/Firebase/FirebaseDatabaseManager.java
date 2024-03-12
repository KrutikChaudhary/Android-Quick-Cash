package com.example.group12.Firebase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.model.Job;
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

/*
DatabaseConnection.java
AUTHOR: Chaz Davies

This file includes a class with all methods related to the database.
 */


public class FirebaseDatabaseManager
{

    private FirebaseDatabase database;
    private DatabaseReference userRef;

    private DatabaseReference jobRef;


    public FirebaseDatabaseManager(){}
    public FirebaseDatabaseManager(FirebaseDatabase database) {
        this.database = database;
        //this.setDBListener();
        this.initializeDatabaseRefs();
    }

//    protected void setDBListener() {
//        this.db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //incomplete method, add your implementation
//                extractedMessage = snapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    protected void initializeDatabaseRefs() {
        userRef = database.getReference().child("User");
    }

    public DatabaseReference saveUserCredentialsToFirebase(String email, String password){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Password", password);

        DatabaseReference dbref = this.userRef.push();
        dbref.setValue(map);
        return dbref;
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

    public List<Job> jobFilter(String parameter, String salary, String duration, String location){
        List<Job> filterdJobList = new ArrayList<>();
        this.getUserRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for (DataSnapshot jobSnapshot : snapshot.getChildren()){
                    Map<String, Object> jobMap = (Map<String, Object>) jobSnapshot.getValue();
                    String jobTitle = (String) jobMap.get("title");
                    String jobSalary = (String) jobMap.get("Salary");
                    String jobDuration = (String) jobMap.get("Duration");
                    String jobStartDate = (String) jobMap.get("Start Date");
                    //LatLng jobLcation = (LatLng) jobMap.get("Location");

                    //do something here


                    Job job = new Job(jobTitle, jobSalary, jobDuration, jobStartDate);
                    filterdJobList.add(job);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return filterdJobList;
    }

}
