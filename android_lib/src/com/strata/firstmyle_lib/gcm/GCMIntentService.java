package com.strata.firstmyle_lib.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.strata.firstmyle_lib.R;

public class GCMIntentService extends IntentService {
    public GCMIntentService() {
        super("GcmIntentService");
    }
    GCMApp app;
    @Override
    public void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        app = (GCMApp) getApplication();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (messageType.equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
            String type = extras.getString("type");
            if ("NTN".equals(type)) {
                Intent notifyIntent = null;
                String activity = extras.getString("activity");
                String url = extras.getString("url");
                if (activity != null) {
                    try {
                        notifyIntent = new Intent(this, Class.forName(activity))
                                .putExtra("fromGCM", true)
                                .putExtra("gcmExtras", extras)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                } else if (url != null) {
                    notifyIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                }
                setUpNotification(notifyIntent, extras);
            } else {
                app.onGCMMessage(extras);
            }
        }
        GCMBroadcastReceiver.completeWakefulIntent(intent);
    }

    public void setUpNotification(Intent intent, Bundle extras){
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("Recent Updates");
        inboxStyle.addLine(extras.getString("message"));
        Uri alarmSound = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(R.drawable.gcm_icon)
                .setContentTitle(extras.getString("title"))
                .setContentText(extras.getString("message"))
                .setStyle(inboxStyle)
                .setNumber(1)
                .setLights(Color.BLUE, 500, 500)
                .setAutoCancel(true)
                .setVibrate(new long[]{100, 250, 100, 250, 100, 250})
                .setSound(alarmSound)
                .setContentIntent(contentIntent);
        app.styleNotification(mBuilder, extras);
        NotificationManager noteMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        noteMgr.notify(1, mBuilder.build());
    }
}
