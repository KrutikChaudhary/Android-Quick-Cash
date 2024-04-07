package com.example.group12.ui.user;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Dashboard_User_PreferredJobs extends AppCompatActivity {
    private EditText editTextPreferredLocation;
    private EditText editTextPreferredSalary;
    private EditText editTextPreferredJobTitle;
    private TextView textViewPreferredLocation;
    private TextView textViewPreferredSalary;
    private TextView textViewPreferredJobTitle;
    private Button buttonSubmit;
    private SharedPreferences preferences;
    private String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_preferred_jobs);
        preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");

        // Initialize views
        editTextPreferredLocation = findViewById(R.id.editTextPreferredLocation);
        editTextPreferredSalary = findViewById(R.id.editTextPreferredSalary);
        editTextPreferredJobTitle = findViewById(R.id.editTextPreferredJobTitle);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        // Set click listener for submit button
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFirebase(key, editTextPreferredLocation.getText().toString(),
                        editTextPreferredSalary.getText().toString(), editTextPreferredJobTitle.getText().toString());
                saveLocally(editTextPreferredLocation.getText().toString(),
                        editTextPreferredSalary.getText().toString(), editTextPreferredJobTitle.getText().toString());
            }
        });

        // Initialize TextViews
        textViewPreferredLocation = findViewById(R.id.textViewPreferredLocation);
        textViewPreferredSalary = findViewById(R.id.textViewPreferredSalary);
        textViewPreferredJobTitle = findViewById(R.id.textViewPreferredJobTitle);

        // Retrieve preferences from Firebase
        retrievePreferencesFromFirebase(key);
    }
    private void saveToFirebase(String key, String preferredLocation, String preferredSalary, String preferredJobTitle) {
        // Check if any preference field is empty
        if (preferredLocation.isEmpty() && preferredSalary.isEmpty() && preferredJobTitle.isEmpty()) {
        // Show toast message for empty preferences
            Toast.makeText(this, "Failed to submit preference. Enter at least one preference!", Toast.LENGTH_SHORT).show();
        } else {
            // Proceed to save preferences to Firebase
            FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
            FirebaseDatabaseManager firebaseDatabaseManager = new FirebaseDatabaseManager(db);
            firebaseDatabaseManager.savePreferenceToFirebase(key, preferredLocation, preferredSalary, preferredJobTitle);
            // Show success message
            Toast.makeText(this, "Preferences submitted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves user preferences locally using SharedPreferences.
     * @param preferredLocation The preferred location to be saved.
     * @param preferredSalary   The preferred salary to be saved.
     * @param preferredJobTitle The preferred job title to be saved.
     */
    private void saveLocally(String preferredLocation, String preferredSalary, String preferredJobTitle){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("salary", preferredSalary);
        editor.putString("location", preferredLocation);
        editor.putString("title", preferredJobTitle);
        editor.apply();
    }

    /**
     * Retrieves user preferences from Firebase for a specific user identified by their key.
     * Updates TextViews with the fetched preference data.
     * @param key The key of the user whose preferences are to be retrieved.
     */
    private void retrievePreferencesFromFirebase(String key) {
    // Get reference to the Firebase database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(key);
    // Add listener for fetching data
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            // Check if preferences exist
                if (snapshot.exists()) {
            // Get preference data
                    String preferredLocation = snapshot.child("PreferredLocation").getValue(String.class);
                    String preferredSalary = snapshot.child("PreferredSalary").getValue(String.class);
                    String preferredJobTitle = snapshot.child("PreferredJobTitile").getValue(String.class);
            // Update TextViews with preference data
                    if(preferredLocation!=null){
                        textViewPreferredLocation.setText("Preferred Location: " + preferredLocation);
                    }
                    if(preferredJobTitle!=null){
                        textViewPreferredSalary.setText("Preferred Salary: " + preferredSalary);
                    }
                    if(preferredJobTitle!=null){
                        textViewPreferredJobTitle.setText("Preferred Job Title: " + preferredJobTitle);
                    }
                } else {
            // If no preferences found, show a message
                    Toast.makeText(Dashboard_User_PreferredJobs.this, "No preferences found.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            // If an error occurs, show a message
                Toast.makeText(Dashboard_User_PreferredJobs.this, "Failed to retrieve preferences: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}