package com.example.group12.util;

import com.google.firebase.database.DatabaseReference;

/**
 * Interface for handling login callbacks.
 */
public interface LoginCallback {
    /**
     * Method called when the login result is received.
     *
     * @param isValid Indicates whether the login was successful or not.
     * @param role    The role of the user (e.g., "Employee" or "Employer").
     * @param key     The key associated with the user in the database.
     */
    void onLoginResult(boolean isValid, String role, String key);
}
