package com.gurpster.facapemobile.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.gurpster.facapemobile.BasePresenter;
import com.gurpster.facapemobile.BaseView;
import com.gurpster.facapemobile.model.Grade;

import java.util.List;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public interface DrawerContract {

    interface View extends BaseView<Presenter> {


        void setUserInfo(String ... data);

        void runLogin();

        void runSplashScreen();

        void setMainActivity(String activity);

    }

    interface Presenter extends BasePresenter<View> {

        void loadPreferences();

        void savePreferences(String ... s);

        void onSplashScreen(Bundle savedInstanceState);

        String getMainActivity();

    }
}
