package com.gurpster.facapemobile.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gurpster.facapemobile.R;
import com.gurpster.facapemobile.util.Constants;
import com.gurpster.facapemobile.view.activities.DrawerActivity;

/**
 * Created by Sistemas on 20/02/2018.
 */

public class NotificationService extends Service {


//    private static final int NOTIFICATION_ID = Integer.valueOf(System.currentTimeMillis());
    private static final String NOTIFICATION_CHANNEL_ID = "my_notification_channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("NotificationService", "onStartCommand()");

        String ticker = intent.getStringExtra(Constants.NOTIFICATION_PARAMETER_1);
        String title = intent.getStringExtra(Constants.NOTIFICATION_PARAMETER_2);
        String message = intent.getStringExtra(Constants.NOTIFICATION_PARAMETER_3);
        runNotification(ticker, title, message);

        return START_REDELIVER_INTENT;
    }

    private void runNotification(CharSequence ticker, CharSequence title, CharSequence message) {

        Intent intent = new Intent(getApplication(), DrawerActivity.class);

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(), 0, intent, 0);

//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "FACAPE_M");
//        builder.setTicker("Não perca sua aula!");
//        builder.setContentTitle("Facape Mobile");
//        builder.setSubText("Subject asdasd");
//        builder.setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(), R.mipmap.ic_launcher));

//        Notification notification = builder.build();

//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setVibrate(new long[]{0, 100, 100, 100, 100, 100})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(ticker)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent);

        Long notifyId = System.currentTimeMillis();
        notificationManager.notify(notifyId.intValue(), builder.build());

    }

}
