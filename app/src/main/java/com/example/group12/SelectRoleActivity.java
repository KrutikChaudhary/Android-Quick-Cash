package com.example.group12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.Toast;

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
        employeeRole();
        employerRole();
    }


    protected void employeeRole() {
        Button employee = findViewById(R.id.selectEmployee);

        employee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                updateRole("Employee");
                //connect to employee dashboard
                Intent a = new Intent(SelectRoleActivity.this, Dashboard_User.class);
                startActivity(a);
                //Test for changing pages
             //   if (a.getName().equals(MainActivity.class)) {
               //     Toast.makeText(SelectRoleActivity.this, "Changed page to employee dashboard", Toast.LENGTH_SHORT).show();
            //    } else {
               //     Toast.makeText(SelectRoleActivity.this, "Error", Toast.LENGTH_SHORT).show();
             //   }
            }
        });
    }
        protected void employerRole() {
            Button employer = findViewById(R.id.selectEmployer);

            employer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    updateRole("Employer");
                    //connect to employer dashboard
                    Intent a = new Intent(SelectRoleActivity.this, Dashboard_Employer.class);
                    startActivity(a);

                    //Test for changing pages
                //    if (a instanceof MainActivity.class) {
                 //       Toast.makeText(SelectRoleActivity.this, "Changed page to employer dashboard", Toast.LENGTH_SHORT).show();
                  //  } else {
                 //       Toast.makeText(SelectRoleActivity.this, "Error", Toast.LENGTH_SHORT).show();
                  //  }
                }
            });
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