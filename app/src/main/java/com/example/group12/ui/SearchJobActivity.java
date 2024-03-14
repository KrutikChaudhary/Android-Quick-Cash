package com.example.group12.ui;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ArrayAdapter;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.Firebase.FirebaseDatabaseManager;

import com.example.group12.R;

import com.example.group12.core.Constants;

import com.example.group12.model.Job;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SearchJobActivity extends AppCompatActivity {

    EditText jobParameterText;

    Spinner salarySpinner;

    Spinner durationSpinner;

    Spinner locationSpinner;

    Button jobSearchButton;

    String parameter = "";

    String salary = "";

    String duration = "";

    String location = "";

    FirebaseDatabaseManager dbManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_job);

        salarySpinnerSetup();

        durationSpinnerSetup();

        locationSpinnerSetup();

        init();

    }

    protected void init(){

        jobParameterText = findViewById(R.id.jobParametersText);

        jobSearchButton = findViewById(R.id.jobSearchButton);

    }

    protected void salarySpinnerSetup(){

        salarySpinner = findViewById(R.id.salarySpinner);

        String[] salaryOptions = {Constants.SPINNER_SALARY_SELECT, Constants.SPINNER_SALARY_RANGE_ONE, Constants.SPINNER_SALARY_RANGE_TWO,

                Constants.SPINNER_SALARY_RANGE_THREE, Constants.SPINNER_SALARY_RANGE_FOUR, Constants.SPINNER_SALARY_RANGE_FIVE, Constants.SPINNER_SALARY_RANGE_SIX};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salaryOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        salarySpinner.setAdapter(adapter);

    }

    protected void durationSpinnerSetup(){

        durationSpinner = findViewById(R.id.durationSpinner);

        String[] durationOptions = {Constants.SPINNER_DURATION_SELECT, Constants.SPINNER_DURATION_RANGE_ONE, Constants.SPINNER_DURATION_RANGE_TWO,

                Constants.SPINNER_DURATION_RANGE_THREE, Constants.SPINNER_DURATION_RANGE_FOUR, Constants.SPINNER_DURATION_RANGE_FIVE, Constants.SPINNER_DURATION_RANGE_SIX};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durationOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        durationSpinner.setAdapter(adapter);

    }

    protected void locationSpinnerSetup(){

        locationSpinner = findViewById(R.id.locationSpinner);

        String[] locationOptions = {Constants.SPINNER_LOCATION_SELECT, Constants.SPINNER_LOCATION_RANGE_ONE, Constants.SPINNER_LOCATION_RANGE_TWO,

                Constants.SPINNER_LOCATION_RANGE_THREE, Constants.SPINNER_LOCATION_RANGE_FOUR, Constants.SPINNER_LOCATION_RANGE_FIVE, Constants.SPINNER_LOCATION_RANGE_SIX};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, locationOptions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        locationSpinner.setAdapter(adapter);

    }

    protected void buttonSetup(){

        jobSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                retrieveUserInput();

                List<Job> options = dbManager.jobFilter(parameter, salary, duration, location);

                Intent searchIntent = new Intent(SearchJobActivity.this, ViewSearchJobActivity.class);
                searchIntent.putExtra("JobList", options.toArray(new Job[options.size()]));
                SearchJobActivity.this.startActivity(searchIntent);
            }

        });

    }

    protected void retrieveUserInput(){

        parameter = jobParameterText.getText().toString();

        salarySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                salary = parent.getItemAtPosition(position).toString();

            }

        });

        durationSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                duration = parent.getItemAtPosition(position).toString();

            }

        });

        locationSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                location = parent.getItemAtPosition(position).toString();

            }

        });

    }

    protected void databaseInit(){

        FirebaseDatabase db = FirebaseDatabase.getInstance(Constants.FIREBASE_LINK);

        dbManager = new FirebaseDatabaseManager(db);

    }

}
