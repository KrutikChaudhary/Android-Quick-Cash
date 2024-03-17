package com.example.group12.logic;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.util.LoginCallback;
import com.example.group12.core.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LoginValidator {
    FirebaseDatabaseManager dbManager;

    private boolean valid = false;
    private String role = "";

    public LoginValidator(){
        dbManager = new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
    }


    public void isValidLogin(String email, String password, LoginCallback callback) {
        valid = false;
        role = "";
        DatabaseReference ref = dbManager.getUserRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean valid = false;
                String role = "";
                for (DataSnapshot user : snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();
                    String email_firebase = (String) userCredentials.get("Email");
                    String password_firebase = (String) userCredentials.get("Password");
                    if (email_firebase.equals(email) && password_firebase.equals(password)) {
                        valid = true;
                        role = (String) userCredentials.get("Role");
                        Log.e("Match", "True");
                        break; // Exit the loop once email is found
                    }
                }
                callback.onLoginResult(valid, role);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onLoginResult(false, "");
            }
        });

    }

    public boolean getValid(){
        return this.valid;
    }

    public String getRole(){
        return this.role;
    }
}
