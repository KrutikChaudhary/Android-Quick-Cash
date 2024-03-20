package com.example.group12.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.R;
import com.example.group12.model.Job;
import com.example.group12.ui.user.Dashboard_User;
import com.example.group12.util.JobListAdapter;
import com.example.group12.util.WrapLinearLayoutManager;

import java.util.Arrays;
import java.util.List;

/**
 * Activity for viewing the list of search results.
 */
public class ViewSearchJobActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button backButton;
    Job[] jobArray;
    List<Job> jobList;
    String key;


    /**
     * Initializes the activity and sets up the UI components.
     *
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_job);
        init();
        jobArray = (Job[])getIntent().getSerializableExtra("JobList");
        jobList = Arrays.asList(jobArray);
        Log.e("Length", "The size of the list: " + jobList.size());
        viewJob();
    }

    /**
     * Initializes the UI components.
     */
    protected void init(){
        recyclerView = findViewById(R.id.filteredJob_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        key = getIntent().getStringExtra("key");
    }

    /**
     * Displays the list of jobs.
     */
    protected void viewJob(){
        JobListAdapter adapter = new JobListAdapter(jobList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the button for navigating back to the user dashboard.
     */
    protected void buttonSetup(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewSearchJobActivity.this, Dashboard_User.class);
                intent.putExtra("key", key);
                ViewSearchJobActivity.this.startActivity(intent);
            }
        });
    }
}
