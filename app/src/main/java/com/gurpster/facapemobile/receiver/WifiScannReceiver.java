package com.gurpster.facapemobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gurpster.facapemobile.service.NotificationService;
import com.gurpster.facapemobile.util.Constants;

/**
 * Created by Williaan Lopes (d3x773r) on 20/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class WifiScannReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra(Constants.NOTIFICATION_PARAMETER_1, "WifiScanner");
        i.putExtra(Constants.NOTIFICATION_PARAMETER_2, "WifiScannReceiver");
        i.putExtra(Constants.NOTIFICATION_PARAMETER_3, "This Work!!!");
        context.startService(i);
    }
}
