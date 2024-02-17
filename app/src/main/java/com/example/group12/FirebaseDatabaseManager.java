package com.example.group12;
import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
DatabaseConnection.java
AUTHOR: Chaz Davies

This file includes a class with all methods related to the database.
 */


public class FirebaseDatabaseManager
{

    private FirebaseDatabase database;
    private DatabaseReference db_ref = null;

    private static String extractedMessage;


    public FirebaseDatabaseManager(){}
    public FirebaseDatabaseManager(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.setDBListener();
    }

    protected void setDBListener() {
        this.db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //incomplete method, add your implementation
                extractedMessage = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void setMessage(String message) {
        //incomplete method, add your implementation
        extractedMessage = message;
        db_ref.child("Message").setValue(message);
    }




    protected void initializeDatabaseRefs() {
        //Incomplete method, add your implementation
        this.db_ref = database.getReference();
    }




    public String getExtractedMessage() {
        return this.extractedMessage;
    }


}
