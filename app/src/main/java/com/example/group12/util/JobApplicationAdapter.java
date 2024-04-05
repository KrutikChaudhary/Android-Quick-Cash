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
import com.example.group12.model.JobApplication;
import com.example.group12.ui.payment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;
/**
 * Adapter for binding JobApplication data to RecyclerView.
 */
public class JobApplicationAdapter extends FirebaseRecyclerAdapter<JobApplication, JobApplicationAdapter.JobApplicationViewHolder> {
    /**
     * Initialize a JobApplicationAdapter.
     *
     * @param options FirebaseRecyclerOptions<JobApplication> object
     * containing query and configuration for FirebaseRecyclerAdapter.
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
    /**
     * ViewHolder class for JobApplicationAdapter.
     */
    public class JobApplicationViewHolder extends RecyclerView.ViewHolder{
        private final TextView name;
        private final Button payToEmployee;
        private final Button acceptApplication;
        private final Button rejectApplication;
        private final TextView statusApplication;
        private final Context context;
        /**
         * Constructor for JobApplicationViewHolder.
         *
         * @param view The view associated with the ViewHolder.
         */
        public JobApplicationViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            payToEmployee = view.findViewById(R.id.PayNow);
            acceptApplication = view.findViewById(R.id.AcceptApplicationButton);
            rejectApplication = view.findViewById(R.id.RejectApplicationButton);
            statusApplication = view.findViewById(R.id.statusMessage);
            context = view.getContext();
        }
    }
    @Override
    protected void onBindViewHolder(@NonNull JobApplicationAdapter.JobApplicationViewHolder holder, int position, @NonNull JobApplication model) {
        if(model.getApplicationStatus().equals("Accepted")){
            holder.acceptApplication.setVisibility(View.GONE);
            holder.rejectApplication.setVisibility(View.GONE);
            holder.payToEmployee.setVisibility(View.VISIBLE);
        } else if(model.getApplicationStatus().equals("Rejected")){
            holder.acceptApplication.setVisibility(View.GONE);
            holder.rejectApplication.setVisibility(View.GONE);
            holder.statusApplication.setVisibility(View.VISIBLE);
        }
        holder.name.setText(model.getName());
        DatabaseReference itemRef = getRef(position);
        String itemKey = itemRef.getKey();
        holder.payToEmployee.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, payment.class);
            String merchantID = model.getMerchantID();
            intent.putExtra("MerchantID",merchantID);
            intent.putExtra("Name",model.getName());
            holder.context.startActivity(intent);
        });

        holder.acceptApplication.setOnClickListener(view -> {
            setApplicationStatusOnFirebase("Accepted",itemKey);
            holder.acceptApplication.setVisibility(View.GONE);
            holder.rejectApplication.setVisibility(View.GONE);
            holder.payToEmployee.setVisibility(View.VISIBLE);
        });
        holder.rejectApplication.setOnClickListener(view -> {
            setApplicationStatusOnFirebase("Rejected",itemKey);
            holder.acceptApplication.setVisibility(View.GONE);
            holder.rejectApplication.setVisibility(View.GONE);
            holder.statusApplication.setVisibility(View.VISIBLE);
        });
    }
    public void setApplicationStatusOnFirebase(String status,String applicationKey){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Job Application").child(applicationKey);
        ref.child("applicationStatus").setValue(status)
                .addOnSuccessListener(aVoid -> {
// This block will be executed once the status is successfully updated.
// Here, you can notify the user of the successful update or log the success.
                    Log.d("UpdateStatus", "Application status updated successfully to: " + status);
                })
                .addOnFailureListener(e -> {
// This block will be executed if there's an error during the update.
// Here, you can notify the user of the failure or log the error.
                    Log.e("UpdateStatus", "Error updating application status", e);
                });
    }
}