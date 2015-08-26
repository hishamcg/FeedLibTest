package com.strata.firstmyle_lib.gcm;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public interface GCMApp {
    void onGCMMessage(Bundle extras);
    void styleNotification(NotificationCompat.Builder builder, Bundle extras);
}
