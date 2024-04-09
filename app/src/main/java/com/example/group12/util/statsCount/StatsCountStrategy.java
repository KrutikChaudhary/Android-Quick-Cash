package com.example.group12.util.statsCount;

import com.example.group12.util.callback.FirebaseCountCallback;

/**
 * This interface defines a strategy for calculating total counts associated with a specific user's email address.
 */
public interface StatsCountStrategy {
    /**
     * Retrieves the total counts associated with the provided email address.
     *
     * @param email    The email address of the user for whom the counts are being retrieved.
     * @param callback A callback to handle the result of the count retrieval operation.
     */
    public void getTotalCounts(String email, FirebaseCountCallback callback);
}
