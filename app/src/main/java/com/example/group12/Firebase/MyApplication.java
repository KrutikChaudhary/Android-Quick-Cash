package com.example.group12.Firebase;

import android.app.Application;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyApplication extends Application {


    // OAuth Client 2 key
    public static final String FIREBASE_SERVER_KEY = "ya29.c.c0AY_VpZifsJY3-RwjHXXpY0u_4ggpPKjAfiENTKhodlhRlhEE3GxUijhFhFLr-YsxpOMXpM-GUS_jt7Do8pyHGqUalUNqoqMkMa-OsWjJPBbXD6bNqW37JIojNCCLclP4GJk56kpV3Bceym2cz9nphDcLmQY1LxxW79HH1RsvZ3jDb6JhFRavgSSFiYYDq7K9gQu-4A3CTB5f9Hf-g5MsnAinVPkPtpSOQ9g4iA5vJx6TKR1ioA-GdCl68Z6s8oX8Dcuyws-BvaHODTw-aa0VbH8AsRpUleSEkY7c1JnpjVMnJaPpY2sWM0xtXEgp58JMUM5piI9h-oBYG1uTe_JzjRFMWamrNZNtfLivCFV471HMyF9i1zANvYiDYM_DM9k0gpHCzQT399DOkQMnXSkVk3q6QtscO0_61l9UQipMn-b_fd6jM9lQISgisrXgwu54eUO91unOzgXiYygwgrcf6czmQo1e0snv-9obo7n6j74uxIlMzsqS48d3Zf76f4ucMciZY8a4kbFw_rsOeZ53lmZWtt-1z8Bdw9_njhQ6c53yzWrdi5QvQOSjsIeyfIaROoop53_rI5fiZmqjUIyMoVh-BX-R-vsoMFQ3cVYokcQMUvBd5u0B_rVjJJzWU6hs9araXWFcFqMWZ8txQspmOl2W_0po-yddh93MJ5OpkIqsvM5ulj8WQcX5o42ZkdSnnYo-nZpp4zkmff4fWF5Q4g0a4cjWtcim-8S9FyW7b0JiZiwj_QeWpeqzy7IFfx7Z-SkbzXVg8iSpBIaesMjOcqOB8exoe5Zg-UY2Iy733zWhI51nRsMZ6USRrvF12xv3o6nhVh_qbFFYMMfFQma_0w5y5MFSYiObd1zkiW6nwZbSM8s5usYYIonz7fwxSR7Owzy17Wxfxlq50x33enpfyIOFbXi2upyyqYMQon2kFzihbMcRMk76eWBsXqyr-f_fqcOqbRpc2p2Bndsu5Z5wuisWUMWh9e_X_QaXSdSO-Ubxm__dgWg6-9J";

    //provided by google - for sending the notification

    //new endpoint
    private static final String PUSH_NOTIFICATION_ENDPOINT ="https://fcm.googleapis.com/v1/projects/quickcash-197c8/messages:send";

    // for the send notification button
    private Button sendNotificationBtn;

    //provided by volley library to make a network request
    private RequestQueue requestQueue;

    FirebaseDatabaseManager dbManager;

    @Override
    public void onCreate(){
        super.onCreate();

    }

    private void init(){
        dbManager = new FirebaseDatabaseManager();
        requestQueue = Volley.newRequestQueue(this);
        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
    }

    private void databaseListener(){
        DatabaseReference dbref = dbManager.getJobRef();
        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Map<String, Object> job = (Map<String, Object>) snapshot.getValue();
                //test if job is a preferred job
                sendNotification(job);
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

    private void sendNotification(Map<String, Object> job){

    }

}
