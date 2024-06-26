package com.example.group12.util.callback;

import com.example.group12.model.job.Job;

import java.util.List;

/**
 * Interface for providing callback method when job filtering is successful.
 */
public interface JobFilterCallback {

    /**
     * Callback method invoked when job filtering is successful.
     *
     * @param jobList List of filtered jobs.
     */
    void onJobFilterSuccess(List<Job> jobList);
}
