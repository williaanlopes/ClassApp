package com.gurpster.facapemobile.view.activities;

import android.os.Bundle;
import android.text.TextUtils;

import com.gurpster.facapemobile.data.source.local.preferences.PreferencesHelper;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class DrawerPresenter implements DrawerContract.Presenter {

    PreferencesHelper preferencesHelper;

    @Nullable
    DrawerContract.View view;

    private String token;
    private String student;

    @Inject
    DrawerPresenter(PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void loadPreferences() {
        token = preferencesHelper.getAccessToken();
//        name = preferencesHelper.getUserName();
//        shortName = preferencesHelper.getUserShortName();
//        course = preferencesHelper.getCourse();
//        period = preferencesHelper.getPeriod();
        student = preferencesHelper.getStudent();

//        if (view != null)
        view.setUserInfo(student);
        view.setMainActivity(preferencesHelper.getMainScreen());
//        onSplashScreen(null);
    }

    @Override
    public void savePreferences(String... s) {
        preferencesHelper.setSplashScreenTime(s[0]);
    }

    @Override
    public void onSplashScreen(Bundle savedInstanceState) {

//        long diff = System.currentTimeMillis() - Long.parseLong(preferencesHelper.getSplashScreenTime());
// || TextUtils.isEmpty(student)
        if( TextUtils.isEmpty(token) || TextUtils.isEmpty(student) ) {
            view.runLogin();
        }
//        else if(diff > 20L) {
//          view.runSplashScreen();
//        }
    }

    @Override
    public String getMainActivity() {
        return preferencesHelper.getMainScreen();
    }

    @Override
    public void takeView(DrawerContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }
}
