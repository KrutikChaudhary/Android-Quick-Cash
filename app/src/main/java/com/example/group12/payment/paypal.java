package com.example.group12.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class paypal extends AppCompatActivity {

    private static final String TAG = paypal.class.getName();

    private ActivityResultLauncher<Intent> intentActivityResultLauncher;

    //for using Paypal related methods
    private PayPalConfiguration payPalConfig;

    //UI Elements
    private EditText enterAmtET;
    private Button payNowBtn;
    private TextView paymentStatusTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        configPayPal();
        initActivityLauncher();
        setListeners();
    }

    private void init() {
        //initializing ui elements
        enterAmtET = findViewById(R.id.enterAmtET);
        payNowBtn = findViewById(R.id.payNowBtn);
        paymentStatusTV = findViewById(R.id.paymentStatusTV);
    }

    private void configPayPal() {
        //configuring paypal i.e defining we're using SANDBOX Environment and setting the paypal client id
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(BuildConfig.PAYPAL_CLIENT_ID);
    }

    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                // Get the payment details
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);
                                // Extract json response and display it in a text view.
                                JSONObject payObj = new JSONObject(paymentDetails);
                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                                paymentStatusTV.setText(String.format("Payment %s%n with payment id is %s", state, payID));
                            } catch (JSONException e) {
                                Log.e("Error", "an extremely unlikely failure occurred: ", e);
                            }
                        }
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Log.d(TAG, "Launcher Result Invalid");
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.d(TAG, "Launcher Result Cancelled");
                    }
                });
    }

    private void setListeners() {
        payNowBtn.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        final String amount = enterAmtET.getText().toString();
        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                amount), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);

        // Create Paypal Payment activity intent
        final Intent intent = new Intent(this, PaymentActivity.class);
        // Adding paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        // Adding paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        // Starting Activity Request launcher
        activityResultLauncher.launch(intent);
    }

}
