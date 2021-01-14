package com.gurpster.facapemobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Williaan Lopes (d3x773r) on 23/12/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class InternetConnectionBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        intent = new Intent("");
//        context.startService(intent);
        Log.d("Broadcast", "internet connection");
    }
}
