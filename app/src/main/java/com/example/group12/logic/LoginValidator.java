package com.example.group12.logic;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginValidator {
    FirebaseDatabaseManager dbManager;

    public boolean valid = false;

    public LoginValidator(){
        dbManager = new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
    }


    public void isValidLogin(String email, String password) {
        DatabaseReference ref = dbManager.getUserRef();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();
                    String email_firebase = (String) userCredentials.get("Email");
                    String password_firebase = (String) userCredentials.get("Password");
                    if (email_firebase.equals(email) && password_firebase.equals(password)) {
                        valid = true;
                        break; // Exit the loop once email is found
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
