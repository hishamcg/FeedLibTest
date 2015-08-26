package com.strata.test.feedlibtest;

import android.app.Application;

import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.LibSharedPref;
import com.strata.firstmyle_lib.utils.LibShowToast;

/**
 * Created by hisham on 9/7/15.
 */
public class TestApp extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        LibSharedPref.initialize(this);
        LibShowToast.initialize(this);
        LibRestClient.init("", "http://10.0.0.37:4000/api/v1/fm_consumer");
    }
}

