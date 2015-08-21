package com.strata.android_lib.gcm;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public interface GCMApp {
    public void onGCMMessage(Bundle extras);
    public void styleNotification(NotificationCompat.Builder builder, Bundle extras);
}
