package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

                String email_text = email.getText().toString();
                String password_text = password.getText().toString();

                if (isValidEmail(email_text) && isValidPassword(password_text)) {
                                    Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                       Toast.makeText(LogIn.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                  }
            }
        });
    }

    private boolean isValidEmail(String email) {
               String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
               Pattern pattern = Pattern.compile(regex);
               Matcher matcher = pattern.matcher(email);
              return matcher.matches();
           }

           private boolean isValidPassword(String password) {
             if (password.length() < 8) {
                       return false;
                    }
               return true;
           }
}