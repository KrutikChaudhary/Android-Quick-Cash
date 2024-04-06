package com.example.group12.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.group12.R;
import com.example.group12.model.Job;
import com.example.group12.ui.employer.EmployerJobDetail;
import com.example.group12.ui.user.JobApplication.JobDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

/**
 * Adapter for binding Job data to RecyclerView.
 */
public class EmployerJobAdapter extends FirebaseRecyclerAdapter<Job, EmployerJobAdapter.EmployeeJobViewHolder>{
    private String email;

    /**
     * Constructs a new JobAdapter.
     *
     * @param options FirebaseRecyclerOptions<Job> object containing query and configuration for FirebaseRecyclerAdapter.
     * @param email   Email of the user.
     */
    public EmployerJobAdapter(@NonNull FirebaseRecyclerOptions<Job> options, String email){
        super(options);
        this.email=email;
    }

    @NonNull
    @Override
    public EmployeeJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_listing_view, parent, false);
        return new EmployeeJobViewHolder(view);
    }

    /**
     * ViewHolder class for JobAdapter.
     */
    public class EmployeeJobViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final Button seeDetailsButton;
        private final Context context;

        /**
         * Constructor for JobViewHolder.
         *
         * @param view The view associated with the ViewHolder.
         */
        public EmployeeJobViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.jobTitle);
            seeDetailsButton = view.findViewById(R.id.seeDetailButton);
            context = view.getContext();
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull EmployeeJobViewHolder holder, int position, @NonNull Job job){
        holder.title.setText(job.getTitle());

        holder.seeDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, EmployerJobDetail.class);
            intent.putExtra("title", job.getTitle());
            intent.putExtra("email",email);
            intent.putExtra("employerEmail",job.getEmployerEmail());
            intent.putExtra("latitude",job.getLatitude());
            intent.putExtra("longitude",job.getLongitude());
            intent.putExtra("description","Description:\nSalary: " + job.getSalary() +"\nDuration: "
                    + job.getDuration() + "\nStart date: "+ job.getStartDate() + "\nUrgency: " + job.getUrgency());
            holder.context.startActivity(intent);
        });
    }
}