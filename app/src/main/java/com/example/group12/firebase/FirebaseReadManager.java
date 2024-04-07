package com.example.group12.firebase;

import androidx.annotation.NonNull;

import com.example.group12.util.EmailCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseReadManager {
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference merchantRef;
    private DatabaseReference jobApplicationRef;
    private DatabaseReference userLocation;
    private DatabaseReference jobRef;

    public FirebaseReadManager(FirebaseDatabase database) {
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
}
