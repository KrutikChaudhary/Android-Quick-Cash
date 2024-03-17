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
import com.example.group12.model.JobApplication;
import com.example.group12.ui.payment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class JobApplicationAdapter extends FirebaseRecyclerAdapter<JobApplication, JobApplicationAdapter.JobApplicationViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public JobApplicationAdapter(@NonNull FirebaseRecyclerOptions<JobApplication> options) {
        super(options);
    }

    @NonNull
    @Override
    public JobApplicationAdapter.JobApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_job_application_view, parent, false);
        return new JobApplicationViewHolder(view);
    }

    public class JobApplicationViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final Button payToEmployee;
        private final Context context;
        public JobApplicationViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            payToEmployee = view.findViewById(R.id.PayNow);
            context = view.getContext();
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull JobApplicationAdapter.JobApplicationViewHolder holder, int position, @NonNull JobApplication model) {
        holder.name.setText(model.getName());
        holder.payToEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, payment.class);
            String merchantID = model.getMerchantID();
            intent.putExtra("MerchantID",merchantID);
            intent.putExtra("Name",model.getName());
            holder.context.startActivity(intent);

        });
    }
}
