package com.example.group12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Dashboard_User_MyPayPal extends AppCompatActivity {
    EditText merchantID;
    Button connectMerchantId;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_mypaypal);
        merchantID = findViewById(R.id.merchant);
        connectMerchantId = findViewById(R.id.connectMyMerchantId);
        email = getIntent().getStringExtra("email");
        connectMerchantId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}