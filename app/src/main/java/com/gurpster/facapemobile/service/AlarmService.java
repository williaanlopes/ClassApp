package com.gurpster.facapemobile.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gurpster.facapemobile.util.AlarmManagerHelper;

import java.util.Calendar;

import static android.app.AlarmManager.RTC;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.KITKAT;

/**
 * Created by Williaan Lopes (d3x773r) on 20/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class AlarmService extends IntentService {

    private static final int PENDING_INTENT_REQUEST_CODE = 0x123;
    private static final String TAG = AlarmService.class.getSimpleName();

    public AlarmService() {
        super("AlarmService");
    }

    /**
     * @return PendingIntent wrapping an Intent that can be used to start this service.
     */
    public static PendingIntent getPendingIntent(final Context context) {
        final Intent intent = new Intent(context, AlarmService.class);
        return PendingIntent.getService(context, PENDING_INTENT_REQUEST_CODE, intent, 0);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        setNextAlarm(this);
    }

    private void setNextAlarm(final Context context) {
        if (SDK_INT >= KITKAT) {

            final Calendar calendar = AlarmManagerHelper.getAlarmCalendar();
            final PendingIntent pendingIntent = AlarmService.getPendingIntent(context);
            AlarmManagerHelper.getAlarmManager(context).setExact(RTC, calendar.getTimeInMillis(), pendingIntent);

            Log.d(TAG, "On KitKat or newer, scheduling next alarm for " + calendar.getTime().toString());
        }
    }
}
