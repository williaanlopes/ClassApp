package com.gurpster.facapemobile;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.di.component.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

public class StartApplication extends DaggerApplication {

//    @Inject
//    GradeRepository gradeRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        Log.d("Dagger", "StartApp");
        return DaggerAppComponent.builder().application(this).build();
    }

    /**
     * Our Espresso tests need to be able to get an instance of the {@link GradeRepository}
     * so that we can delete all tasks before running each test
     */
//    @VisibleForTesting
//    public GradeRepository getGradeRepository() {
//        return gradeRepository;
//    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }
}
