package com.example.group12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.util.Log;

import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class SelectRoleActivity extends AppCompatActivity {

    String userKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userKey = getIntent().getStringExtra("key");
        Log.e("key", userKey);
        setContentView(R.layout.activity_select_role);
        updateRole("Buyer");
    }


    protected void employeeRole(){
        Button employee = findViewById(R.id.selectEmployee);
        Button employer = findViewById(R.id.selectEmployer);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //connect to employee dashboard
                Intent a = new Intent(SelectRoleActivity.this, DashBoard_User);
                startActivity(a);
                //add role to database
            }
        }
    }
    protected void employerRole(){
    Button employer = findViewById(R.id.selectEmployer);

        employer.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            //connect to employer dashboard
            Intent a = new Intent(SelectRoleActivity.this, DashBoard_Employer);
            startActivity(a);
            //add role to database

        }
    }
}

    protected void updateRole(String role){
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(userKey);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, Object> user = (Map<String, Object>) snapshot.getValue();
                if (user != null){
                    user.put("Role", role);
                    userRef.setValue(user);
                }
                else{
                    Log.e("null user", "User map is null");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}