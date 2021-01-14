package com.gurpster.facapemobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;
import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.view.activities.LoginActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.DaggerActivity;

/**
 * Created by Williaan Lopes (d3x773r) on 05/11/2017.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class Main extends DaggerActivity {

    private static final boolean DEBUG = false;
    private static final String TAG = "APP";

    @Inject
    PreferencesHelper preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (LeakCanary.isInAnalyzerProcess(getApplication())) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//
//        LeakCanary.install(getApplication());

        ButterKnife.bind(this);
        Intent intent;

        String authToken = preferences.getAccessToken();
        boolean firstAccess = preferences.getFirstAccess();
        String login = preferences.getLogin();
        String password = preferences.getPassword();
//        String defaultTimetable = preferences.getDefaultTimeTable();

//        if(defaultTimetable == null) {
//            preferences.setDefaultTimeTable("0");
//            defaultTimetable = "0";
//        }

        if(DEBUG) Log.d(TAG, "authToken: " + authToken + " firstAccess:" + firstAccess + " login:" + login + " password: " + password);

//        if(firstAccess) {
//            intent = new Intent(this, MainActivity.class);
//        } else
            if(TextUtils.isEmpty(authToken) && (TextUtils.isEmpty(login) || TextUtils.isEmpty(password))) {
            intent = new Intent(this, LoginActivity.class);
        }
        else {
            intent = new Intent(this, DrawerActivity.class);
//            intent.putExtra("defaultTimetable", defaultTimetable);
        }

        startActivity(intent);
        finish();
    }
}
