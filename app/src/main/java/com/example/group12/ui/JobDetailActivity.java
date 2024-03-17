package com.example.group12.ui;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import com.example.group12.R;
import com.example.group12.model.Job;
public class JobDetailActivity extends AppCompatActivity {
    String title;
    String desc;
    TextView jobTitle;
    TextView jobDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        jobTitle = findViewById(R.id.jobDetailsTitle);
        jobDesc = findViewById(R.id.jobDetails);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("description");
        setTextView();
    }
    public void setTextView(){
        jobTitle.setText(title);
        jobDesc.setText(desc);
    }
}
