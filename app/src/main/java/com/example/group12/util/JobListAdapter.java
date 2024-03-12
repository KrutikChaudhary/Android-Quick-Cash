package com.example.group12.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.R;
import com.example.group12.model.Job;

import java.util.List;


public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {
    private List<Job> jobList;

    // Constructor to initialize the list of Job objects
    public JobListAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_listing_view, parent, false);
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        // Bind data to the views in each RecyclerView item
        Job job = jobList.get(position);
        holder.bind(job);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    // ViewHolder class to hold references to views within each item of the RecyclerView
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private Button seeDetailButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.jobTitle);
            seeDetailButton = itemView.findViewById(R.id.seeDetailButton);
        }

        public void bind(Job job) {
            // Bind data from Job object to views
            titleTextView.setText(job.getTitle());
        }
    }
}

