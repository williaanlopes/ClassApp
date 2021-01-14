package com.gurpster.facapemobile.di.module;

import com.gurpster.facapemobile.di.PreferenceContext;
import com.gurpster.facapemobile.util.Constants;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Williaan Lopes (d3x773r) on 19/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public class PreferenceModule {

    @Provides
    @PreferenceContext
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }
}
