package com.example.group12.logic;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.group12.Firebase.FirebaseDatabaseManager;
import com.example.group12.core.Constants;
import com.example.group12.util.MerchantIDCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MerchantIDValidator {
    FirebaseDatabaseManager dbManager;

    private boolean valid = false;

    public MerchantIDValidator(){
        dbManager = new FirebaseDatabaseManager(FirebaseDatabase.getInstance(Constants.FIREBASE_LINK));
    }

    public void isMerchantIDConnected(String email, MerchantIDCallBack callback) {
        valid = false;

        DatabaseReference ref = dbManager.getMerchantRef();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean valid = false;
                String merchant = "";
                for (DataSnapshot user : snapshot.getChildren()){
                    Map<String, Object> userCredentials = (Map<String, Object>) user.getValue();
                    String email_firebase = (String) userCredentials.get("Email");
                    String merchantID_firebase = (String) userCredentials.get("MerchantID");
                    if (merchantID_firebase!=null && !merchantID_firebase.equals("") && email.equals(email_firebase)) {
                        valid = true;
                        merchant = merchantID_firebase;
                        Log.e("Match", "True");
                        break;
                    }
                }
                callback.merchantIdAvailableResult(valid, merchant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.merchantIdAvailableResult(false, "");
            }
        });

    }

    public boolean isValid() {
        return valid;
    }

}
