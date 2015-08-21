package com.strata.android_lib.singin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.common.SignInButton;
import com.strata.android_lib.R;
import com.strata.android_lib.model.AuthToken;
import com.strata.android_lib.rest.LibRestClient;
import com.strata.android_lib.signin_otp.Authentication;
import com.strata.android_lib.utils.NetworkUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SignupActivity extends Activity {
    private ActionProcessButton btnSignIn;
    private EditText email;
    private EditText password;
    ProgressDialog progress;
//    private LoginButton btnFbSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);
        progress = new ProgressDialog(this);
        progress.setTitle("Signing In");
        progress.setMessage("Please Wait...");

        btnSignIn = (ActionProcessButton) findViewById(R.id.button1);
        btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        SignInButton btnGoogleSignIn = (SignInButton) findViewById(R.id.btn_google_signin);
        email = (EditText) findViewById(R.id.user_email);
        password = (EditText) findViewById(R.id.user_password);
        btnSignIn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (NetworkUtils.isNetworkAvailable(SignupActivity.this)) {
                    progress.show();
                    LibRestClient.getRouteService().userLogin(email.getText().toString(), password.getText().toString(), "", callback);
//                    toast("Internet Connection! Exists");
                } else {
//                    no_connection.setVisibility(View.VISIBLE);
                    toast("NO internet Connection!");
                }
            }
        });
        btnGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), test.class));
            }
        });
    }
    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private final Callback<AuthToken> callback = new Callback<AuthToken>() {
        @Override
        public void failure(RetrofitError retrofitError) {
            progress.dismiss();
            showTimeoutError();
            btnSignIn.setProgress(-1);
        }

        @Override
        public void success(final AuthToken authToken, Response response) {
            progress.dismiss();
            if (authToken != null) {
                btnSignIn.setProgress(100);
//                Intent checking_auth = new Intent(getApplicationContext(), SignInWaitingActivity.class)
//                    .putExtra("NUMBER", phone_no)
//                    .putExtra("pass_key", authToken.getPass_key())
//                    .putExtra("baseUrl", baseUrl);
//                startActivityForResult(checking_auth, Authentication.REQUEST_CODE);

                Intent intent = new Intent();
                intent.putExtra("AUTH_TOKEN", authToken.getAuth_token());
                intent.putExtra("USER_ID", authToken.getUser_id());
                if(authToken.getFirstTime()!=null)
                    intent.putExtra("FIRST_TIME", authToken.getFirstTime());
                intent.putExtra("EMAIL", email.getText().toString());
                setResult(Authentication.RESULT_CODE, intent);
                finish();
                toast("Login Successfull!");
            } else {
                btnSignIn.setProgress(-1);
                toast("Login Failed. Please try after some time");
            }
        }
    };

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
        new AlertDialog.Builder(SignupActivity.this)
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
