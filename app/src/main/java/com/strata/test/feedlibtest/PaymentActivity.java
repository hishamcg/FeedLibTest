package com.strata.test.feedlibtest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.strata.firstmyle_lib.payment.PaymentInitializer;
import com.strata.firstmyle_lib.payment.WebPage;
import com.strata.firstmyle_lib.utils.LibSharedPref;
import com.strata.firstmyle_lib.utils.LibShowToast;

/**
 * Created by nagashree on 3/9/15.
 */
public class PaymentActivity extends AppCompatActivity implements WebPage.OnPaymentInteractionListener {
    private static Context context;
    String feed_id,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_activity);
        context = this;
        feed_id = "123";
        amount = "1";

        PaymentInitializer.initPaymentConfig();
        Button pay = (Button) findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentInitializer.start(PaymentActivity.this,feed_id,amount);
            }
        });
    }

    @Override
    public void onPaymentCompletion(String feed_id, String response) {
        LibShowToast.setText(response);
    }
}
