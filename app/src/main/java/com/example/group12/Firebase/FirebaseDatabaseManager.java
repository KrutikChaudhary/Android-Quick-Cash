package com.example.group12.Firebase;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

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

//    protected void setDBListener() {
//        this.db_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //incomplete method, add your implementation
//                extractedMessage = snapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }


    protected void initializeDatabaseRefs() {
        userRef = database.getReference("User");
    }

    public void saveUserCredentialsToFirebase(String email, String password){
        Map<String, Object> map = new HashMap<>();
        map.put("Email", email);
        map.put("Password", password);

        userRef.setValue(map);
    }

    public DatabaseReference getUserRef(){
        return this.userRef;
    }
}
