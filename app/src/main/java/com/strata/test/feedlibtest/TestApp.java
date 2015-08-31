package com.strata.test.feedlibtest;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.strata.firstmyle_lib.gcm.GCMApp;
import com.strata.firstmyle_lib.gcm.GCMRegisterUtils;
import com.strata.firstmyle_lib.rest.LibRestClient;
import com.strata.firstmyle_lib.utils.LibSharedPref;
import com.strata.firstmyle_lib.utils.LibShowToast;

/**
 * Created by hisham on 9/7/15.
 */
public class TestApp extends Application implements GCMApp {
    @Override
    public void onCreate(){
        super.onCreate();
        LibSharedPref.initialize(this);
        LibShowToast.initialize(this);
        LibRestClient.init("kM21kxymCjtGXVZxTxzG", Config.LIB_URL);
        GCMRegisterUtils.registerGCM(this, Config.GOOGLE_PROJECT_ID, Config.GCM_URL, "kM21kxymCjtGXVZxTxzG");
    }

    @Override
    public void onGCMMessage(Bundle extras) {

    }

    @Override
    public void styleNotification(NotificationCompat.Builder builder, Bundle extras) {

    }
}

