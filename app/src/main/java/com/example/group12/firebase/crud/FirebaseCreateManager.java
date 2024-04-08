package com.example.group12.firebase.crud;

import com.example.group12.model.LocationInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FirebaseCreateManager {
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference merchantRef;
    private DatabaseReference jobApplicationRef;
    private DatabaseReference userLocation;
    private DatabaseReference jobRef;

    /**
     * Parameterized constructor to initialize FirebaseCreateManager with a specified database instance.
     * @param database FirebaseDatabase instance
     */
    public FirebaseCreateManager(FirebaseDatabase database) {
        this.database = database;
        initializeDatabaseRefs();
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
}
