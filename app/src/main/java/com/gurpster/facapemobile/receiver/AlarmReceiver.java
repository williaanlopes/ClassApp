package com.gurpster.facapemobile.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.view.activities.DrawerActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Sistemas on 20/02/2018.
 */

public class AlarmReceiver extends BroadcastReceiver {

//    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";
    private static final String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Alarme", "Alarmando!");

//        Intent i = new Intent("ALARM_SERVICE");
//        context.startService(i);
        runNotification(context);
    }

    private void runNotification(Context context) {

        Intent intent = new Intent(context, DrawerActivity.class);
        intent.putExtra("Alarm", true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setTicker("Ticker Text")
                .setContentTitle("Content Title")
                .setContentText("Content Text")
                .setContentIntent(pendingIntent);

        notificationManager.notify(R.mipmap.ic_launcher, builder.build());

    }
}
