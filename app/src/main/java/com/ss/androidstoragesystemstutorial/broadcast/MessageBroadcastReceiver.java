package com.ss.androidstoragesystemstutorial.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.ss.androidstoragesystemstutorial.R;

/**
 * Created by user on 2/1/2018.
 */

public class MessageBroadcastReceiver extends BroadcastReceiver {
    public static final String EXTRA_KEY_NEW_MESSAGE = "message";

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra(EXTRA_KEY_NEW_MESSAGE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Notification notification = builder.setContentTitle("New Message")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_message_notification)
                .build();
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1001,notification);

    }
}
