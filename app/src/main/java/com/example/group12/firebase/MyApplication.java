package com.example.group12.firebase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.group12.core.Constants;
import com.example.group12.logic.FacadeFilter;
import com.example.group12.util.AccessTokenListener;
import com.example.group12.util.FetchPreferencesCallback;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import com.google.auth.oauth2.GoogleCredentials;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the Application class for the Firebase integration.
 * It handles Firebase initialization, database operations, and notification sending.
 */
public class MyApplication extends Application {

    private static final String CREDENTIALS_FILE_PATH = "cloudMessagingKey.json";

    float MAX_DIST = 20;

    //new endpoint
    private static final String PUSH_NOTIFICATION_ENDPOINT ="https://fcm.googleapis.com/v1/projects/quickcash-197c8/messages:send";


    //provided by volley library to make a network request
    private RequestQueue requestQueue;

    FirebaseDatabaseManager dbManager;

    public SharedPreferences user;
    private String salaryPreferences;
    public String titlePreferences;

    private String locationPreferences;
    private boolean salaryMatch = true;
    private boolean titleMatch = true;

    private boolean locationMatch = true;

    public MyApplication(){

    }
    @Override
    public void onCreate(){
        super.onCreate();
        init();
    }

    /**
     * Initializes Firebase, SharedPreferences, and other necessary components.
     */
    private void init(){
        user = getSharedPreferences("user", Context.MODE_PRIVATE);
        databaseInit();
        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
        databaseListener();
    }

    /**
     * Initializes the Firebase Database.
     */
    private void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }

    /**
     * Retrieves the access token required for sending notifications.
     * @param context Application context
     * @param listener Access token listener
     */
    private void getAccessToken(Context context, AccessTokenListener listener) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                InputStream serviceAccountStream = context.getAssets().open(CREDENTIALS_FILE_PATH);
                GoogleCredentials googleCredentials = GoogleCredentials
                        .fromStream(serviceAccountStream)
                        .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));

                googleCredentials.refreshIfExpired(); // This will refresh the token if it's expired
                String token = googleCredentials.getAccessToken().getTokenValue();
                listener.onAccessTokenReceived(token);
            } catch (IOException e) {
                listener.onAccessTokenError(e);
            }
        });
        executorService.shutdown();
    }

    /**
     * Sets up a listener for changes in the Firebase database.
     */

    private void databaseListener(){
        DatabaseReference dbref = dbManager.getJobRef();
        dbref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> job = (Map<String, Object>) snapshot.getValue();
                //test if job is a preferred job
                fetchPreferences(new FetchPreferencesCallback() {
                    @Override
                    public void onFectchPreferencesCompleted() {
                        retrievePreference();
                        if (checkPreferenceExist()) {
                            float jobSalary = ((Number) job.get("salary")).floatValue();
                            String title = (String) job.get("title");
                            float jobLat = ((Number) job.get("latitude")).floatValue();
                            float jobLong = ((Number) job.get("longitude")).floatValue();

                            if (salaryPreferences == null) {
                                salaryMatch = true;
                            } else {
                                checkSalaryMatch(jobSalary);
                                if(!salaryMatch){
                                    Log.d("Salary does not match", "Salary False");
                                }
                            }
                            Log.d("title Preference", titlePreferences);
                            if (titlePreferences == null) {
                                titleMatch = true;
                            } else {
                                checkTitleMatch(title);
                                if(!titleMatch){
                                    Log.d("Title does not match", "Title False");
                                }
                            }
                            if(locationPreferences == null){
                                locationMatch = true;
                            } else{
                                checkLocationMatch(jobLat, jobLong);
                                if(!locationMatch){
                                    Log.d("Location does not match", "Location False");
                                }
                            }



                            if (salaryMatch && titleMatch && locationMatch) {
                                getAccessToken(getApplicationContext(), new AccessTokenListener() {
                                    @Override
                                    public void onAccessTokenReceived(String token) {
                                        sendNotification(job, token);
                                    }

                                    @Override
                                    public void onAccessTokenError(Exception exception) {
                                        // Handle the error appropriately
                                        Log.d("Token Error", "Error getting access token: " + exception.getMessage());
                                        exception.printStackTrace();
                                    }
                                });
                            }else{
                                Log.d("ERRor", "Does not send notification");
                            }

                        }
                        else{
                            Log.d("No user preference", "true");
                        }
                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

    /**
     * Adapted from Usmi
     * @param job
     * @param token
     */
    private void sendNotification(Map<String, Object> job, String token){
        try {
            String jobLocation = (String) job.get("location");
            long jobSalary = (long) job.get("salary");
            String jobTitle = (String) job.get("Title");
            //the first json object - to
            JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "A job that you prefer!");
            notificationJSONBody.put("body", "A new job is posted. Tap to view details.");

            // Create the data JSON object
            JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("jobLocation", jobLocation);
            dataJSONBody.put("jobSalary", String.valueOf(jobSalary));
            dataJSONBody.put("jobTitle", jobTitle);

            // Create the message JSON object and attach notification and data
            JSONObject messageJSONBody = new JSONObject();
            messageJSONBody.put("topic", "jobs");
            messageJSONBody.put("notification", notificationJSONBody);
            messageJSONBody.put("data", dataJSONBody);

            // Create the final push notification JSON object and attach the message
            JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("message", messageJSONBody);
            //parameters sent in the request:
            //type of request - post- sending data to firebase
            //url - push notification endpoint
            //data - body of the notification
            //toast message
            //error listener
            Log.d("LOG", pushNotificationJSONBody.toString());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushNotificationJSONBody,
                    //lamda syntax
                    response ->
                            Log.d("Notification sent", "True"),
                    //method reference
                    Throwable::printStackTrace) {

                //adding the header to the endpoint
                //parameters used:
                //type of data
                //using the bearer token for authentication of the network request
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + token);
                    Log.d("headers", headers.toString());
                    return headers;
                }
            };
            //add the request to the queue
            requestQueue.add(request);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    public void retrievePreference(){

        salaryPreferences = user.getString("salary", null);
        titlePreferences = user.getString("title", null);
        locationPreferences = user.getString("location", null);
    }

    //This method fetches user preferences from the database and stored locally
    public void fetchPreferences(FetchPreferencesCallback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getString("key", ""));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> userMap = (Map<String, Object>) snapshot.getValue();
                SharedPreferences.Editor editor = user.edit();
                String salary = (String) userMap.get("PreferredSalary");
                String location = (String) userMap.get("PreferredLocation");
                String title = (String) userMap.get("PreferredJobTitile");
                if (salary != null){
                    editor.putString("salary", salary);
                }
                else{
                    editor.putString("salary", null);
                }
                if (location != null){
                    editor.putString("location", location);
                }
                else{
                    editor.putString("location", null);
                }
                if (title != null){
                    editor.putString("title", title);
                }
                else {
                    editor.putString("title", null);
                }
                editor.apply();

                callback.onFectchPreferencesCompleted();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Checks if user preferences exist.
     * @return True if preferences exist, otherwise false
     */
    private boolean checkPreferenceExist(){
        if (salaryPreferences == null && titlePreferences == null && locationPreferences == null){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Checks if the job salary matches the user's preference.
     * @param jobSalary Salary of the job
     */
    public void checkSalaryMatch(float jobSalary){
        int preferenceInt = Integer.parseInt(salaryPreferences);
        if (jobSalary >= preferenceInt - 5 && jobSalary <= preferenceInt + 5){
            salaryMatch = true;
        }
        else{
            salaryMatch = false;
        }
    }

    /**
     * Checks if the job location matches the user's preference.
     * @param jobLat Latitude of job location
     * @param jobLong Longitude of job location
     */
    public void checkLocationMatch(float jobLat, float jobLong){
        float[] coords = getCoords(locationPreferences);

        if(coords == null){
            locationMatch = false;
        }

        else{
            FacadeFilter filter = new FacadeFilter();

            float prefLat = coords[0];
            float prefLong = coords[1];
            Log.d("Coords", "Lat = " + prefLat + " Long = " + prefLong);
            double dist = filter.getDist(jobLat, jobLong, prefLat, prefLong);

            Log.d("Distance", "Distance is " + dist + " kms");


            locationMatch = (MAX_DIST >= dist);
        }
    }

    /**
     * Retrieves latitude and longitude coordinates from the job location string.
     * @param jobLocation Job location
     * @return Array containing latitude and longitude
     */
    public  float[] getCoords(String jobLocation){
        // Get latitude and longitude of the job location
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        float[] coords = new float[2];
        try {
            addresses = geocoder.getFromLocationName(jobLocation,1);
            if(addresses != null){
                Address address = addresses.get(0);
                coords[0] = (float)address.getLatitude();
                coords[1] = (float)address.getLongitude();
            } else{
                return null;
            }

        } catch (IOException e) {
            return null;
        }

        return coords;
    }

    /**
     * Checks if the job title matches the user's preference.
     * @param title Title of the job
     */
    public void checkTitleMatch(String title){
        if (title.toLowerCase().contains(titlePreferences.toLowerCase())){
            titleMatch = true;
            Log.d("Title", "Title Matches");
        }
        else{
            titleMatch = false;
            Log.d("Title", "Title Does not match");

        }
    }

}
