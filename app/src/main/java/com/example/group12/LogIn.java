package com.example.group12;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {

    EditText email;
    EditText password;
    Button loginButton;
    FirebaseDatabase db;
    DatabaseReference userRef;
    Map<String, Object> user;
    boolean ValidEmail=false;
    boolean ValidPassword=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance(getResources().getString(R.string.FIREBASE_LINK));
// test();
        setContentView(R.layout.activity_log_in);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_text = email.getText().toString();
                String password_text = password.getText().toString();
                isValidLogin(email_text,password_text);
                if (ValidEmail&&ValidPassword) {
                    Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LogIn.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void test(){
        DatabaseReference myRef = db.getReference("test");
        myRef.setValue("testing");
    }
    protected DatabaseReference getUserRef() {
        return this.db.getReference().child("User");
    }
    protected void isValidLogin(String email, String password) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();
                    String email_firebase = (String) userCredentials.get("Email");
                    String password_firebase = (String) userCredentials.get("Password");
                    if (email_firebase.equals(email)) {
                        ValidEmail = true;
                        if (password_firebase.equals(password)) {
                            ValidPassword = true;
                        }
                        break; // Exit the loop once email is found
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}