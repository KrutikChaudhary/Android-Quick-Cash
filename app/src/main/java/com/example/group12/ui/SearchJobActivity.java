package com.example.group12.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;

public class SearchJobActivity  extends AppCompatActivity {

    EditText jobParameterText;

    Spinner salarySpinner;

    Spinner durationSpinner;

    Spinner locationSpinner;

    Button jobSearchButton;

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
}
