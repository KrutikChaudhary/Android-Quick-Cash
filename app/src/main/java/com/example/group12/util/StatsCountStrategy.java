package com.example.group12.util;

public interface StatsCountStrategy {
    public void getTotalCounts(String email, FirebaseCountCallback callback);
}
