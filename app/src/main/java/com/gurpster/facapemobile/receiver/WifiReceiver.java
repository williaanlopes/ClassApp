package com.gurpster.facapemobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.gurpster.facapemobile.service.GradeService;

/**
 * Created by Williaan Lopes (d3x773r) on 20/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class WifiReceiver extends BroadcastReceiver {

    private final static String TAG = WifiReceiver.class.getSimpleName();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())
                && WifiManager.WIFI_STATE_ENABLED == wifiState) {

            if (Log.isLoggable(TAG, Log.VERBOSE)) {
                Log.v(TAG, "Wifi is now enabled");
            }

//            Intent i = new Intent(context, GradeService.class);
//            i.putExtra("wifi", true);
//            context.startService(i);
        }
    }
}
