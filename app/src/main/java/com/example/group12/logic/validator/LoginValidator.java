package com.example.group12.logic.validator;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.firebase.crud.FirebaseReadManager;
import com.example.group12.util.callback.LoginCallback;
import com.example.group12.core.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * The LoginValidator class provides methods to validate user login credentials against
 * Firebase user data.
 */
public class LoginValidator {
    FirebaseReadManager dbManager;
    private static LoginValidator instance;
    private boolean valid = false;
    private String role = "";

    String key;

    /**
     * Default constructor for the LoginValidator class.
     * Initializes the FirebaseDatabaseManager instance.
     */
    public LoginValidator(){
        dbManager = new FirebaseReadManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
    }

    // Public method to get the instance
    public static synchronized LoginValidator getInstance() {
        if (instance == null) {
            instance = new LoginValidator();
        }
        return instance;
    }


    /**
     * Validates the user login credentials against Firebase user data.
     *
     * @param email The user's email address.
     * @param password The user's password.
     * @param callback The callback interface for handling the login result.
     */
    public void isValidLogin(String email, String password, LoginCallback callback) {
        // Resetting valid and role flags before querying the database
        valid = false;
        role = "";
        // Getting a reference to the User node in the Firebase database
        DatabaseReference ref = dbManager.getUserRef();
        // Adding a listener to retrieve data from the User node
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Iterating through each child node in the User node
                for (DataSnapshot user : snapshot.getChildren()){

                    // Retrieving user credentials from the database
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();
                    String emailFirebase = (String) userCredentials.get("Email");
                    String passwordFirebase = (String) userCredentials.get("Password");

                    // Checking if the retrieved email and password match the provided credentials
                    if (emailFirebase.equals(email) && passwordFirebase.equals(password)) {

                        // Setting valid to true and retrieving the user's role
                        valid = true;
                        role = (String) userCredentials.get("Role");
                        key = user.getKey();

                        // Logging a match between provided and database credentials
                        Log.e("Match", "True");

                        // Exiting the loop once a match is found
                        break;
                    }
                }

                // Calling the login callback method with the result and role information
                callback.onLoginResult(valid, role, key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handling database error by calling the login callback with false result
                callback.onLoginResult(false, "", "");
            }
        });
    }


    /**
     * Retrieves the validity of the login credentials.
     *
     * @return true if the credentials are valid, false otherwise.
     */
    public boolean getValid(){
        return this.valid;
    }

    /**
     * Retrieves the role associated with the user.
     *
     * @return The role associated with the user.
     */
    public String getRole(){
        return this.role;
    }
}
