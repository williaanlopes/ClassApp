package com.gurpster.facapemobile;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by Williaan Lopes (d3x773r) on 23/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class CreateAlarm extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent("FM_ALARM");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.MINUTE, 15);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME, calendar.getTimeInMillis(), (1000 * 900), pendingIntent);
    }
}
