package com.example.group12.ui.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.R;
import com.example.group12.core.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity class for connecting a PayPal merchant ID in the user dashboard.
 * Users can input their PayPal merchant ID to connect to their account.
 */
public class Dashboard_User_MyPayPal extends AppCompatActivity {
    EditText merchantEditText;
    Button connectMerchantId;
    String email;
    FirebaseDatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_user_mypaypal);
        merchantEditText = findViewById(R.id.merchant);
        connectMerchantId = findViewById(R.id.connectMyMerchantId);
        email = getIntent().getStringExtra("email");
        connectMerchantId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String merchantID = merchantEditText.getText().toString();
                DatabaseReference ref = null;
                if(merchantID!=null && !merchantID.equals("")){
                    ref = saveMerchantIDToFirebase(email,merchantID);
                }
                if(ref !=null){
                    // Display success message and navigate to user dashboard
                    Toast.makeText(Dashboard_User_MyPayPal.this,"Merchant Id Saved Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dashboard_User_MyPayPal.this, Dashboard_User.class);
                    intent.putExtra("user_email", email);
                    Dashboard_User_MyPayPal.this.startActivity(intent);
                } else {
                    // Display error message if saving fails
                    Toast.makeText(Dashboard_User_MyPayPal.this,"Merchant Id is not Saved try again!!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Saves the PayPal merchant ID to Firebase database.
     *
     * @param email      User's email address
     * @param merchantID PayPal merchant ID
     * @return DatabaseReference pointing to the saved merchant ID
     */
    public DatabaseReference saveMerchantIDToFirebase(String email, String merchantID){
        dbManager=new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
        DatabaseReference ref = dbManager.saveMerchantIDtoFirebase(email,merchantID);
        return ref;
    }
}