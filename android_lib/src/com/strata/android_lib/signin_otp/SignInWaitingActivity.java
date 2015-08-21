package com.strata.android_lib.signin_otp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.strata.android_lib.R;
import com.strata.android_lib.model.AuthToken;
import com.strata.android_lib.rest.LibRestClient;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignInWaitingActivity extends Activity {
    private BroadcastReceiver mIntentReceiver;
    private TextView timerTv;
    private ActionProcessButton otpButton;
    private EditText otp_input;
    private String pass_key;
    private String phone;
    private String email;
    static Boolean timeOut = true;
    private CountDownTimer count_down;
    private ProgressBar SW_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_sign_in_waiting);
        String baseUrl = getIntent().getStringExtra("baseUrl");
        Intent auth_val = getIntent();
        email = auth_val.getStringExtra("email");
//        phone = auth_val.getStringExtra("phone");
        pass_key = auth_val.getStringExtra("pass_key");

        int waiting_time = 600;

        timerTv = (TextView) findViewById(R.id.SW_TimeRemainigTv);
        otpButton = (ActionProcessButton) findViewById(R.id.btn_otp);
        otpButton.setMode(ActionProcessButton.Mode.ENDLESS);
        otp_input = (EditText) findViewById(R.id.SW_OtpText);
        SW_progressBar = (ProgressBar)findViewById(R.id.SW_progressBar);
        TextView SW_MobNoVeryfyDesctxt = (TextView)findViewById(R.id.SW_MobNoVeryfyDesctxt);
        SW_MobNoVeryfyDesctxt.setText("Your SMS Verification code has been sent to +91"+phone);

        // show 300 second time count down
        count_down = new CountDownTimer(waiting_time * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int a = (int) millisUntilFinished / 1000;
                timerTv.setText("Time Remaining : " +a/60+ ":" +a%60);
            }

            @Override
            public void onFinish() {
                finish();
            }

        }.start();
        otpButton.setOnClickListener(new Button.OnClickListener() {
            @SuppressLint("InlinedApi")
            public void onClick(View v) {
                String otp_code = otp_input.getText().toString();
                if (!otp_code.isEmpty()) {
                    AuthenticationComplete(otp_code);
                } else {
                    toast("please enter the OTP");
                }

            }
        });
    }

    public void AuthenticationComplete(String password) {
        otpButton.setProgress(1);
        timeOut = false;
        count_down.cancel();
        LibRestClient.getRouteService().validateOTP(password, phone, new Callback<AuthToken>() {
            @Override
            public void failure(RetrofitError retrofitError) {
                toast("Login Failed. Please try after some time");
                otpButton.setProgress(-1);
            }

            @Override
            public void success(final AuthToken authToken, Response response) {
                if (authToken.getSuccess() != null && authToken.getSuccess()) {
                    otpButton.setProgress(100);
                    Intent intent = new Intent();
                    intent.putExtra("AUTH_TOKEN", authToken.getAuth_token());
                    intent.putExtra("phone", phone);
                    setResult(Authentication.RESULT_CODE, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");
                // Process the sms format and extract body &amp; phoneNumber
                msg = msg.replace("\n", "");
                if (pass_key!=null && msg.startsWith(pass_key)) {
                    String mOTP = msg.substring(msg.length() - 6);
                    otp_input.setText(mOTP);
                    SW_progressBar.setVisibility(View.GONE);
                    AuthenticationComplete(mOTP);
                    //AuthenticationComplete(msg.substring(msg.length() - 6));
                }
            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (count_down != null)
            count_down.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(this.mIntentReceiver);
    }

}

