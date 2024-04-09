package com.example.group12.logic.validator;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.core.Constants;
import com.example.group12.firebase.crud.FirebaseReadManager;
import com.example.group12.util.callback.MerchantIDCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * This class provides functionality to validate merchant IDs in the Firebase database.
 */
public class MerchantIDValidator {
    FirebaseReadManager dbManager;

    private boolean valid = false;

    /**
     * Constructs a new MerchantIDValidator instance.
     */
    public MerchantIDValidator(){
        dbManager = new FirebaseReadManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
    }

    /**
     * Checks if a merchant ID is connected to a specific email address.
     * @param email The email address to check for the connected merchant ID.
     * @param callback The callback interface to handle the result of the operation.
     */
    public void isMerchantIDConnected(String email, MerchantIDCallBack callback) {
        // Initialize the validity flag as false
        valid = false;

        // Get the reference to the "MerchantID" node in the Firebase database
        DatabaseReference ref = dbManager.getMerchantRef();

        // Add a listener to read the data at this location once
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Initialize local variables for validity and merchant ID
                boolean isValid = false;
                String merchant = "";

                // Iterate through each child node under "MerchantID"
                for (DataSnapshot user : snapshot.getChildren()) {
                    // Get the user credentials from the snapshot
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();

                    // Extract email and merchant ID from user credentials
                    String emailFirebase = (String) userCredentials.get("Email");
                    String merchantIDFirebase = (String) userCredentials.get("MerchantID");
                    if (merchantIDFirebase != null && !merchantIDFirebase.equals("") && email.equals(emailFirebase)) {
                        isValid = true;
                        merchant = merchantIDFirebase;

                        // Log the match found
                        Log.e("Match", "True");

                        // Exit the loop since a match is found
                        break;
                    }
                }

                // Call the callback method to pass the validity and merchant ID information
                callback.merchantIdAvailableResult(isValid, merchant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // If the operation is canceled, invoke the callback with false validity and empty merchant ID
                callback.merchantIdAvailableResult(false, "");
            }
        });
    }

    /**
     * Gets the validity of the merchant ID.
     * @return True if the merchant ID is valid; otherwise, false.
     */
    public boolean isValid() {
        return valid;
    }
}
