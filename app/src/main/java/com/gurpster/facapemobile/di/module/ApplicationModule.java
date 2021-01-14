package com.gurpster.facapemobile.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public abstract class ApplicationModule {

    //expose StartApplication as an injectable context
    @Binds
    abstract Context provideContext(Application application);
//    abstract Context bindContext(Application application);

}