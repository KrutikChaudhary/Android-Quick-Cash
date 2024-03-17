
/*
DatabaseConnection.java
AUTHOR: Chaz Davies

This file includes a class with all methods related to the database.
 */

package com.example.group12.Firebase;
import android.util.Log;
import androidx.annotation.NonNull;
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
        userRef = database.getReference().child("User");
        jobRef = database.getReference().child("Job");
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
                    String jobSalary = (String) jobMap.get("salary");
                    String jobDuration = (String) jobMap.get("duration");
                    String jobStartDate = (String) jobMap.get("startDate");

                    //LatLng jobLcation = (LatLng) jobMap.get("Location");
                    String location = (String) jobMap.get("location");


                    // include job location
//                    if(containsParameters(parameter, jobTitle) && containsSalary(salary, jobSalary) && containsDuration(duration, jobDuration)){
//                        Job job = new Job(jobTitle, jobSalary, jobDuration, jobStartDate, location);
//                        filterdJobList.add(job);
//                    }
                    if (containsParameters(parameter, jobTitle)){
                        Job job = new Job(jobTitle, jobSalary, jobDuration, jobStartDate, location);
                        filterdJobList.add(job);
                        Log.e("size of filtered list", "Size is: " + filterdJobList.size());
                    }

                    callback.onJobFilterSuccess(filterdJobList);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    protected boolean containsParameters(String param, String title){
        return title.contains(param);
    }

    protected boolean containsSalary(String param, String salary){

        if(param.equals("")){
            return true;
        }

        String sal = String.valueOf(salary.charAt(0)) + String.valueOf(salary.charAt(1));
        boolean results = false;

        int num = Integer.parseInt(sal);

        if(param.equals(Constants.SPINNER_SALARY_RANGE_ONE)){
            if(num >= 15) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_TWO)){
            if(num >= 20) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_THREE)){
            if(num >= 30) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_FOUR)){
            if(num >= 40) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_FIVE)){
            if(num >= 50) {
                results = true;
            }
        }
        else if(param.equals(Constants.SPINNER_SALARY_RANGE_SIX)){
            if(num >= 100) {
                results = true;
            }
        }

        return results;
    }

    protected boolean containsDuration(String param, String duration){

        if(param.equals("")) return true;

        boolean result = false;

        if(duration.equals(Constants.SPINNER_DURATION_RANGE_ONE)){
            result = param.equals(duration);
        }
        else if(duration.equals(Constants.SPINNER_DURATION_RANGE_TWO)){
            result = param.equals(Constants.SPINNER_DURATION_RANGE_ONE) || param.equals(duration);
        }
        else if(duration.equals(Constants.SPINNER_DURATION_RANGE_THREE)){
            result = param.equals(Constants.SPINNER_DURATION_RANGE_ONE) || param.equals(Constants.SPINNER_DURATION_RANGE_TWO) || param.equals(duration);
        }
        else if(duration.equals(Constants.SPINNER_DURATION_RANGE_FOUR)){
            result = param.equals(Constants.SPINNER_DURATION_RANGE_ONE) || param.equals(Constants.SPINNER_DURATION_RANGE_TWO) || param.equals(Constants.SPINNER_DURATION_RANGE_THREE)|| param.equals(duration);
        }
        else if(duration.equals(Constants.SPINNER_DURATION_RANGE_FIVE)){
            result = param.equals(Constants.SPINNER_DURATION_RANGE_ONE) || param.equals(Constants.SPINNER_DURATION_RANGE_TWO) || param.equals(Constants.SPINNER_DURATION_RANGE_THREE)||  param.equals(Constants.SPINNER_DURATION_RANGE_FOUR)||param.equals(duration);
        }
        else if(duration.equals(Constants.SPINNER_DURATION_RANGE_SIX)){
            result = param.equals(Constants.SPINNER_DURATION_RANGE_ONE) || param.equals(Constants.SPINNER_DURATION_RANGE_TWO) || param.equals(Constants.SPINNER_DURATION_RANGE_THREE)||  param.equals(Constants.SPINNER_DURATION_RANGE_FOUR)||param.equals(Constants.SPINNER_DURATION_RANGE_FIVE)||param.equals(duration);
        }
        return result;
    }

    protected boolean inDistance(String param, String location){
        if(param.equals("")){
            return true;
        }

        // get user location

//        double distance = getDistance();
//         distance = user location - job location <- euclidean
//
//
//        if(param.equals(Constants.SPINNER_LOCATION_RANGE_ONE)){
//            if(.5 >= distance){
//                return true;
//            }
//        }
//        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_TWO)){
//            if(1.0 >= distance){
//                return true;
//            }
//        }
//        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_THREE)){
//            if(2.0 >= distance){
//                return true;
//            }
//        }
//        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_FOUR)){
//            if(3.0 >= distance){
//                return true;
//            }
//        }
//        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_FIVE)){
//            if(5.0 >= distance){
//                return true;
//            }
//        }
//        else if(param.equals(Constants.SPINNER_LOCATION_RANGE_SIX)){
//            if(10.0 >= distance){
//                return true;
//            }
//        }

        // if distiance is in param return true
        return false;
    }


    // return euclidean distance
    protected double getDistance(Double lat_x, Double lng_x, Double lat_y, Double lng_y){
        // convert degree to km
        double km_lat = (lat_x - lat_y)/110.574; //
        double km_lng = (lng_x - lng_y)/Math.cos((lat_x - lat_y) * Math.PI / 180);

        // eaclidean distance formula
        double km_x = km_lng*km_lng;
        double km_y = km_lat*km_lat;

        return Math.sqrt(km_x + km_y);
    }

}