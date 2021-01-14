package com.gurpster.facapemobile.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by Williaan Lopes (d3x773r) on 16/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class ScheduledServices extends Service {

    @Inject
    PreferencesHelper preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
//        threads = new ArrayList<>();
//        if(DEBUG) Log.d(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
