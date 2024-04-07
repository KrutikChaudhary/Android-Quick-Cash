package com.example.group12.firebase.crud;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class FirebaseUpdateManager {

    private FirebaseDatabase database;

    public FirebaseUpdateManager(FirebaseDatabase database) {
        this.database = database;
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


}
