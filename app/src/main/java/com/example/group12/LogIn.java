package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;
import java.util.regex.Pattern;

public class LogIn extends AppCompatActivity {


    EditText email;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(email.getText().toString().equals("user") && password.getText().toString().equals("12345")){
                    Toast.makeText(LogIn.this, "Login successful", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(LogIn.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                }
                


            }
        });









    }
}