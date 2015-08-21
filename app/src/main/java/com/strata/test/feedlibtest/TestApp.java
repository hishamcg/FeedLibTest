package com.strata.test.feedlibtest;

import android.app.Application;

import com.strata.android_lib.rest.LibRestClient;
import com.strata.android_lib.utils.LibSharedPref;
import com.strata.android_lib.utils.LibShowToast;

/**
 * Created by hisham on 9/7/15.
 */
public class TestApp extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        LibSharedPref.initialize(this);
        LibShowToast.initialize(this);
        LibRestClient.init("","http://www.firstmiler.com/api/consumer/v1/login");
    }
}

