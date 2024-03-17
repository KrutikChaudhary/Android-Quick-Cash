package com.example.group12.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group12.R;
import com.example.group12.model.Job;
import com.example.group12.util.JobListAdapter;
import com.example.group12.util.WrapLinearLayoutManager;

import java.util.Arrays;
import java.util.List;

public class ViewSearchJobActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button backButton;
    Job[] jobArray;
    List<Job> jobList;


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


    protected void init(){
        recyclerView = findViewById(R.id.filteredJob_recyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }
    protected void viewJob(){
        JobListAdapter adapter = new JobListAdapter(jobList);
        recyclerView.setAdapter(adapter);

    }

}
