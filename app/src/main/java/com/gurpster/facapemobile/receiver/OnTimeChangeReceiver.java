package com.gurpster.facapemobile.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gurpster.facapemobile.util.SimpleAlarmManager;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by Williaan Lopes (d3x773r) on 31/05/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */
public class OnTimeChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // To get set of all registered ids on boot.
        Set<String> set = SimpleAlarmManager.getAllRegistrationIds(context);
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            int id = Integer.parseInt(it.next());
            //to initialize with registreation id
            SimpleAlarmManager.initWithId(context, id).start();
        }
    }
}