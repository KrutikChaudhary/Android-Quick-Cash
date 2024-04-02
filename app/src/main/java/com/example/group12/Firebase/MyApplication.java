package com.example.group12.Firebase;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.group12.core.Constants;
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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

    private static final String CREDENTIALS_FILE_PATH = "cloudMessagingKey.json";


    //new endpoint
    private static final String PUSH_NOTIFICATION_ENDPOINT ="https://fcm.googleapis.com/v1/projects/quickcash-197c8/messages:send";


    //provided by volley library to make a network request
    private RequestQueue requestQueue;

    FirebaseDatabaseManager dbManager;

    private SharedPreferences user;
    private String salaryPreferences;
    private String titlePreferences;
    private boolean salaryMatch = true;
    private boolean titleMatch = true;
    @Override
    public void onCreate(){
        super.onCreate();
        init();
    }

    private void init(){
        user = getSharedPreferences("user", Context.MODE_PRIVATE);
        databaseInit();
        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
        databaseListener();
    }

//    private void getCountOfItemsInDatabase() {
//        DatabaseReference dbRef = dbManager.getJobRef();
//        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                orginalCount = dataSnapshot.getChildrenCount();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("Database Error", "Error fetching item count: " + databaseError.getMessage());
//            }
//        });
//    }

    private void databaseInit(){
        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        dbManager = new FirebaseDatabaseManager(db);
    }

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
                            String title = (String) job.get("Title");
                            if (salaryPreferences == null) {
                                salaryMatch = true;
                            } else {
                                checkSalaryMatch(jobSalary);
                            }
                            if (titlePreferences == null) {
                                titleMatch = true;
                            } else {
                                checkTitleMatch(title);
                            }

                            if (salaryMatch && titleMatch) {
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
    }

    public void fetchPreferences(FetchPreferencesCallback callback){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User").child(user.getString("key", ""));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> userMap = (Map<String, Object>) snapshot.getValue();
                SharedPreferences.Editor editor = user.edit();
                String salary = (String) userMap.get("PreferredSalary");
                String location = (String) userMap.get("PreferredLocation");
                String title = (String) userMap.get("PreferredJobTitle");
                if (salary != null){
                    editor.putString("salary", salary);
                    Log.e("Preferred Salary", salary);
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
    private boolean checkPreferenceExist(){
        if (salaryPreferences == null && titlePreferences == null){
            return false;
        }
        else{
            return true;
        }
    }

    private void checkSalaryMatch(float jobSalary){
        int preferenceInt = Integer.parseInt(salaryPreferences);
        if (jobSalary >= preferenceInt - 5 && jobSalary <= preferenceInt + 5){
            salaryMatch = true;
        }
        else{
            salaryMatch = false;
        }
    }

    private void checkTitleMatch(String title){
        if (title.toLowerCase().contains(titlePreferences.toLowerCase())){
            titleMatch = true;
        }
        else{
            titleMatch = false;
        }
    }

}
