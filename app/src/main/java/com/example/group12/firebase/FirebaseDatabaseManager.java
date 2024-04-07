package com.example.group12.firebase;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.locationDetection.LocationInfo;
import com.example.group12.util.EmailCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import com.example.group12.logic.FilterJob;
import com.example.group12.model.Job;
import com.example.group12.util.JobFilterCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * FirebaseDatabaseManager class contains methods related to database operations.
 * This includes saving user credentials, job details, merchant IDs, job applications,
 * user locations, and updating user roles in Firebase Realtime Database.
 */
public class FirebaseDatabaseManager
{
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference merchantRef;
    private DatabaseReference jobApplicationRef;
    private DatabaseReference userLocation;
    private DatabaseReference jobRef;

    private SharedPreferences preferences;

    /**
     * Default constructor.
     */
    public FirebaseDatabaseManager(){}

    /**
     * Parameterized constructor to initialize FirebaseDatabaseManager with a specified database instance.
     * @param database FirebaseDatabase instance
     */
    public FirebaseDatabaseManager(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
    }

    /**
     * Initializes database references.
     */
    protected void initializeDatabaseRefs() {
        userRef = database.getReference().child("User");
        userLocation = database.getReference().child("Location");
        jobRef = database.getReference().child("Job");
        merchantRef = database.getReference().child("MerchantID");
        jobApplicationRef = database.getReference().child("Job Application");

    }

    /**
     * Saves user credentials to Firebase database.
     * @param email User email
     * @param password User password
     * @return DatabaseReference for the saved user credentials
     */
    public DatabaseReference saveUserCredentialsToFirebase(String email, String password, String role){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Password", password);
        map.put("Role", role);
        DatabaseReference dbref = this.userRef.push();
        dbref.setValue(map);
        return dbref;
    }

    /**
     * Saves job details to Firebase database.
     * @param jobTitle Title of the job
     * @param date Start date of the job
     * @param expectedDuration Expected duration of the job
     * @param urgency Urgency of the job
     * @param salary Salary for the job
     * @param jobLocation Location of the job
     * @param latitude Latitude of the job location
     * @param longitude Longitude of the job location
     */
    public void saveJobsToFirebase(String employerEmail, String jobTitle, String date, int expectedDuration, String urgency,
                                   float salary, String jobLocation, float latitude, float longitude){
        Map<String, Object> map = new HashMap<>();

        map.put("employerEmail", employerEmail);
        map.put("title", jobTitle);
        map.put("startDate", date);
        map.put("duration", expectedDuration);
        map.put("urgency", urgency);
        map.put("salary", salary);
        map.put("location", jobLocation);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        DatabaseReference dbref = this.jobRef.push();
        dbref.setValue(map);
    }

    /**
     * Saves merchant ID to Firebase database.
     * @param email Merchant email
     * @param merchantID Merchant ID
     * @return DatabaseReference for the saved merchant ID
     */
    public DatabaseReference saveMerchantIDtoFirebase(String email,String merchantID){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("MerchantID", merchantID);

        DatabaseReference dbref = this.merchantRef.push();
        dbref.setValue(map);
        return dbref;
    }


    public DatabaseReference saveJobApplicationToFirebase(String employeeEmail, String employerEmail, String jobTitle, String employeeName, String employeeMerchantID){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", employeeEmail);
        map.put("employerEmail", employerEmail);
        map.put("Name", employeeName);
        map.put("jobTitle", jobTitle);
        map.put("MerchantID", employeeMerchantID);
        map.put("applicationStatus", "InReview");
        DatabaseReference dbref = this.jobApplicationRef.push();
        dbref.setValue(map);
        return dbref;
    }


    /**
     * Saves location details to Firebase database.
     * @param locationInfo LocationInfo object containing location details
     */
    public void saveLocationToFirebase(LocationInfo locationInfo){
        Map<String, Object> locationMap = new HashMap<>();
        locationMap.put("latitude",  locationInfo.getLatitude());
        locationMap.put("longitude", locationInfo.getLongitude());
        locationMap.put("address", locationInfo.getAddress());
        locationMap.put("locality", locationInfo.getLocality());
        locationMap.put("countryCode", locationInfo.getCountryCode());
        DatabaseReference dbref = this.userLocation.push();
        dbref.setValue(locationMap);
    }

    /**
     * Updates user role in Firebase database.
     * @param role New role to be updated
     * @param userKey Key of the user in the database
     */
    public void updateRole(String role, String userKey) {
        // Get DatabaseReference for the user's role under the "User" node in the Firebase database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(userKey);

        // Listen for a single value event to retrieve the user's current role
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve user data as a Map from the DataSnapshot
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();

                // Check if user data is not null
                if (user != null) {
                    // Update the user's role with the new role value
                    user.put("Role", role);

                    // Set the updated user data back to the database
                    ref.setValue(user);
                } else {
                    // Log an error message if user data is null
                    Log.e("null user", "User map is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error if any
            }
        });
    }

    public void savePreferenceToFirebase(String key, String preferredLocation, String preferredSalary, String preferredJobTitle){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve user data as a Map from the DataSnapshot
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();

                if (user != null) {
                // Update the user's preference with the new preference
                    user.put("PreferredLocation", preferredLocation);
                    user.put("PreferredSalary", preferredSalary);
                    user.put("PreferredJobTitile", preferredJobTitle);
                // Set the updated user data back to the database
                    ref.setValue(user);
                } else {
                    // Log an error message if user data is null
                    Log.e("null user", "User map is null");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
}