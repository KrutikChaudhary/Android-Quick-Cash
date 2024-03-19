package com.example.group12.util;

import com.google.firebase.database.DatabaseReference;

public interface LoginCallback {
    void onLoginResult(boolean isValid, String role, String key);
}
