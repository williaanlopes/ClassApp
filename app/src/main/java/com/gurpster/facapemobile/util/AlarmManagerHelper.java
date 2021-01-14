package com.gurpster.facapemobile.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gurpster.facapemobile.receiver.AlarmReceiver;
import com.gurpster.facapemobile.service.AlarmService;
import com.gurpster.facapemobile.view.activities.DrawerActivity;

import java.util.Calendar;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.content.Context.ALARM_SERVICE;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;
import static com.github.mikephil.charting.charts.Chart.LOG_TAG;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Williaan Lopes (d3x773r) on 20/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class AlarmManagerHelper {

    private static final int NOTIFICATION_HOUR = 23;
    private static final int PENDING_INTENT_REQUEST_CODE = 0x123;
    private static final String TAG = AlarmManagerHelper.class.getSimpleName();

    public static AlarmManager getAlarmManager(final Context context) {
        return (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public static Calendar getAlarmCalendar() {
        final Calendar now = Calendar.getInstance();
        final Calendar alarmCalendar = Calendar.getInstance();

        int min = alarmCalendar.get(Calendar.MINUTE);

        alarmCalendar.set(Calendar.HOUR_OF_DAY, 12);
        alarmCalendar.set(Calendar.MINUTE, 0);
        alarmCalendar.set(Calendar.SECOND, 0);
        alarmCalendar.set(Calendar.MILLISECOND, 0);

//        alarmCalendar.add(Calendar.MINUTE, 1);

        // fudge factor to avoid potential issues when re-setting the alarm on KitKat+
        if (alarmCalendar.getTimeInMillis() < now.getTimeInMillis() + SECONDS.toMillis(1)) {
            alarmCalendar.add(Calendar.DATE, 1);
        }

        return alarmCalendar;
    }

    public static void setAlarm(final Context context) {
            final Calendar calendar = getAlarmCalendar();
//            final PendingIntent pendingIntent = AlarmService.getPendingIntent(context);
        Intent intent = new Intent(context, AlarmReceiver.class);
//        Intent intent = new Intent("FACAPE_MOBILE_ALARM");
        PendingIntent pendingIntent = PendingIntent.getService(context, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d(TAG, "Initializing alarm for " + calendar.getTime().toString());

        if (SDK_INT >= KITKAT) {
            getAlarmManager(context).setExact(RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            getAlarmManager(context).setRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL_DAY, pendingIntent);
        }
    }

//        public static void cancelAlarm(final Context context) {
//            Log.d(TAG, "Canceling alarm.");
//            getAlarmManager(context).cancel(AlarmService.getPendingIntent(context));
//        }

}
