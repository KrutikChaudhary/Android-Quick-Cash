package com.example.group12.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.R;
import com.example.group12.model.Job;
import com.example.group12.ui.JobDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class JobAdapter extends FirebaseRecyclerAdapter<Job, JobAdapter.JobViewHolder>{

    public JobAdapter(@NonNull FirebaseRecyclerOptions<Job> options){
        super(options);
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_listing_view, parent, false);
        return new JobViewHolder(view);
    }

    public class JobViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final Button seeDetailsButton;

        private final Context context;

        public JobViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.jobTitle);
            seeDetailsButton = view.findViewById(R.id.seeDetailButton);
            context = view.getContext();
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull Job job){
        holder.title.setText(job.getTitle());
        //Log.e("title", job.getTitle());

        holder.seeDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, JobDetailActivity.class);
            intent.putExtra("key", getRef(position).getKey());
            intent.putExtra("job", job);
            holder.context.startActivity(intent);
        });
    }
}
