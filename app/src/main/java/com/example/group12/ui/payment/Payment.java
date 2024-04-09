package com.example.group12.ui.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group12.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Activity for processing payments via PayPal.
 */
public class Payment extends AppCompatActivity {
    EditText editText;
    Button button;
    String merchantID;
    String employeeName;
    public static PayPalConfiguration configuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        // Initialize UI elements
        editText = findViewById(R.id.amntPay);
        button = findViewById(R.id.pay);

        // Retrieve merchant ID and employee name from intent
        merchantID = getIntent().getStringExtra("MerchantID");
        employeeName = getIntent().getStringExtra("Name");

        // PayPal configuration setup
        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(merchantID);

        // Set onClickListener for the payment button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });
    }

    /**
     * Initiates the payment process.
     */
    private void getPayment(){
        String amount = editText.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)),"CAD",employeeName, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,123);

    }

    /**
     * Handles the result of the activity after payment processing.
     *
     * @param requestCode The request code passed to startActivityForResult().
     * @param resultCode  The result code returned by the child activity.
     * @param data        An Intent that carries the result data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123){
            PaymentConfirmation paymentConfirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            if(paymentConfirmation!=null){
                try{
                    String details = paymentConfirmation.toJSONObject().toString();
                    JSONObject object = new JSONObject(details);
                } catch (JSONException e){
                    Toast.makeText(this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            else if(requestCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Canceled",Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode==PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this,"Invalid Payment",Toast.LENGTH_SHORT).show();
        }
    }
}
