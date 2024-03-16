package com.example.group12.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.Dashboard_User_MyPayPal;
import com.example.group12.R;
import com.example.group12.logic.MerchantIDValidator;
import com.example.group12.model.Job;
import com.example.group12.ui.Dashboard_User;
import com.example.group12.ui.JobApply;
import com.example.group12.ui.JobDetailActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class JobAdapter extends FirebaseRecyclerAdapter<Job, JobAdapter.JobViewHolder>{
    private String email;
    public JobAdapter(@NonNull FirebaseRecyclerOptions<Job> options, String email){
        super(options);
        this.email=email;
        //Log.d("JobAdapter", "Email received: " + this.email);
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
        private final Button applyToJob;

        private final Context context;

        public JobViewHolder(View view){
            super(view);
//            email = emailId;
            title = view.findViewById(R.id.jobTitle);
            seeDetailsButton = view.findViewById(R.id.seeDetailButton);
            applyToJob = view.findViewById(R.id.applyButton);
            context = view.getContext();
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull Job job){
        holder.title.setText(job.getTitle());

        holder.seeDetailsButton.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, JobDetailActivity.class);
            intent.putExtra("key", getRef(position).getKey());
            intent.putExtra("job", job);
            holder.context.startActivity(intent);
        });

        holder.applyToJob.setOnClickListener(view -> {
//            Intent applyIntent = new Intent(holder.context, JobApply.class);
//            applyIntent.putExtra("email", email);
//            //Log.d("JobAdapter", "Email received: " + email);
//            holder.context.startActivity(applyIntent);
            MerchantIDValidator validator = new MerchantIDValidator();
            validator.isMerchantIDConnected(email, new MerchantIDCallBack() {
                @Override
                public void merchantIdAvailableResult(boolean isValid, String merchantID) {
                    if(isValid){
                        Intent applyIntent = new Intent(holder.context, JobApply.class);
                        applyIntent.putExtra("email", email);
                        Log.d("JobAdapter", "Email received: " + email);
                        holder.context.startActivity(applyIntent);
                    } else {
                        Intent applyIntent = new Intent(holder.context, Dashboard_User_MyPayPal.class);
                        applyIntent.putExtra("email", email);
                        Toast.makeText(holder.context, "Add your Merchant ID first", Toast.LENGTH_SHORT).show();
                        holder.context.startActivity(applyIntent);
                    }
                }
            });
        });
    }
}
