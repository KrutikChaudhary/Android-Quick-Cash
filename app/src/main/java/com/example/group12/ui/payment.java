package com.example.group12.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class payment extends AppCompatActivity {
    private static final String TAG = payment.class.getName();

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    //for using Paypal related methods
    public static PayPalConfiguration configurtion;

    //UI Elements
    EditText editText;
    String merchantId;
    Button button;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        button = findViewById(R.id.pay);
        editText = findViewById(R.id.amntPay);
        name = getIntent().getStringExtra("name");
        merchantId = getIntent().getStringExtra("MerchantID");

        configurtion = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(merchantId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPay();
            }
        });
    }

    private void getPay() {
        String amt = editText.getText().toString();
        Intent intent = new Intent(this, PaymentActivity.class);
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amt)), "CAD", name, PayPalPayment.PAYMENT_INTENT_SALE);
    }


    protected void activityResult(int request, int result, @Nullable Intent data) {
        super.onActivityResult(request, result, data);

        if (request == 123) {
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (paymentConfirmation != null) {
                try {
                    String details = paymentConfirmation.toJSONObject().toString();
                    JSONObject o = new JSONObject(details);
                } catch (JSONException e) {
                    Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (request == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            } else if (request == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Not valid payment", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
