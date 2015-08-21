package com.strata.android_lib.signin_otp;

import android.app.Activity;
import android.content.Intent;

import com.strata.android_lib.singin.SignupActivity;

public class Authentication {
    public static final int REQUEST_CODE = 18709;
    public static final int RESULT_CODE = 99876;

    public static String getAuthResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null){
            return data.getStringExtra("AUTH_TOKEN");
        }
        return null;
    }

    public static String getFirstTime(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null){
            String f = data.getStringExtra("FIRST_TIME");
            return f;
        }
        return null;
    }

    public static void start(Activity activity,String baseUrl){
        Intent in = new Intent(activity, SignupActivity.class);
        in.putExtra("baseUrl",baseUrl);
        activity.startActivityForResult(in, REQUEST_CODE);
    }
}
