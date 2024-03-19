package com.example.group12.util;

import com.example.group12.model.Job;

import java.util.List;

public interface JobFilterCallback {
    void onJobFilterSuccess(List<Job> jobList);
}
