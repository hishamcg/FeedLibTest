package com.strata.firstmyle_lib.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.strata.firstmyle_lib.utils.AppUtils;

import java.io.IOException;

import me.pushy.sdk.Pushy;
import retrofit.client.Response;

public class GCMRegisterUtils {

    private static final String PREF = "gcm";
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";
    private static final String REGISTERED = "registered";

    public static void registerGCM(final Context context, final String googleProjectId,
                                   final String appServerUrl, final String authToken) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String regId = getRegistrationId(context);
                    if (regId.isEmpty()) {
                        regId = GoogleCloudMessaging.getInstance(context).register(googleProjectId);
                        //regId = Pushy.register(context);

                        storeRegistrationId(context, regId);
                    }
                    Boolean registered = getPref(context).getBoolean(REGISTERED, false);
                    if (!registered) {
                        GCMRestClient restClient = new GCMRestClient(appServerUrl, authToken);
                        Response r = restClient.getGcmRegisterService().gcmShare(regId);
                        if (r.getStatus() == 200) {
                            getPref(context).edit().putBoolean(REGISTERED, true).apply();
                        } else {
                            throw new IOException("Server status " + r.getStatus());
                        }
                    }
                } catch (Exception ex) {
                    Log.e("GCM","FAILED GCM REGISTRATION >>> "+ex);
                    //Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
    }

    private static String getRegistrationId(Context context) {
        final SharedPreferences pref = getPref(context);
        String registrationId = pref.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        int registeredVersion = pref.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = AppUtils.getVersion(context);
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(PREF,
                Context.MODE_PRIVATE);
    }

    public static void storeRegistrationId(Context context, String regId) {
        getPref(context).edit()
                .putString(REG_ID, regId)
                .putInt(APP_VERSION, AppUtils.getVersion(context))
                .putBoolean(REGISTERED, false)
                .commit();
    }
}
