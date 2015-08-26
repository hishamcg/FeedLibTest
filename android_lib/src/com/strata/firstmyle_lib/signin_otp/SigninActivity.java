package com.strata.firstmyle_lib.signin_otp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.strata.firstmyle_lib.R;
import com.strata.firstmyle_lib.model.AuthToken;
import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.NetworkUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SigninActivity extends Activity {
    private String phone_no;
    private ActionProcessButton btnSignIn;
    private String baseUrl;
    private EditText my_numb;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_signin_layout);

        baseUrl = getIntent().getStringExtra("baseUrl");
        btnSignIn = (ActionProcessButton) findViewById(R.id.button1);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        final TextView no_connection = (TextView) findViewById(R.id.no_connection);
        my_numb = (EditText) findViewById(R.id.enter_number);
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        if (number != null) {
            my_numb.setText(CorrectPhoneFormat(number));
        }

        btnSignIn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(SigninActivity.this)) {
                    phone_no = CorrectPhoneFormat(my_numb.getText().toString());
                    if (phone_no!=null) {
                        btnSignIn.setProgress(1);
                        progress = new ProgressDialog(getApplicationContext());
                        progress.setTitle("Signing In");
                        progress.setMessage("Please Wait...");
                        progress.show();
                        LibRestClient.getRouteService().userAuthenticate(phone_no, callback);
                    }else
                        toast("invalid number");
                } else {
                    no_connection.setVisibility(View.VISIBLE);
                    toast("NO internet Connection!");
                }
            }
        });
    }
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private final Callback<AuthToken> callback = new Callback<AuthToken>() {
        @Override
        public void failure(RetrofitError retrofitError) {
            showTimeoutError();
            progress.dismiss();
            btnSignIn.setProgress(-1);
        }

        @Override
        public void success(final AuthToken authToken, Response response) {
            progress.dismiss();
            if (authToken != null) {
                btnSignIn.setProgress(100);
                Intent checking_auth = new Intent(getApplicationContext(), SignInWaitingActivity.class)
                    .putExtra("phone", phone_no)
                    .putExtra("pass_key", authToken.getPass_key())
                    .putExtra("baseUrl", baseUrl);
                startActivityForResult(checking_auth, Authentication.REQUEST_CODE);
            } else {
                btnSignIn.setProgress(-1);
                toast("Login Failed. Please try after some time");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String authToken = Authentication.getAuthResult(requestCode, resultCode, data);
        if(authToken != null) {
            setResult(resultCode, data);
            finish();
        }else{
            btnSignIn.setProgress(-1);
            toast("Login Failed. Please try after some time");
        }
    }

    public String CorrectPhoneFormat(String number) {
        if (number.length() >= 10) {
            number = number.substring(number.length() - 10);
            return number;
        }
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        btnSignIn.setEnabled(true);
    }

    private void showTimeoutError() {
        new AlertDialog.Builder(SigninActivity.this)
                .setTitle("Connection Time Out!")
                .setMessage("We were not able to reach the server. Please try again after some time")
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(0, null);
                        finish();
                    }
                })
                .setCancelable(true)
                .setIcon(R.drawable.gcm_icon)
                .show();
    }

}
