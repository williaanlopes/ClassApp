package com.gurpster.facapemobile.di.component;

import android.app.Application;

import com.gurpster.facapemobile.StartApplication;
import com.gurpster.facapemobile.data.source.GradeRepository;
import com.gurpster.facapemobile.di.module.ActivityBindingModule;
import com.gurpster.facapemobile.di.module.ApiModule;
import com.gurpster.facapemobile.di.module.AppDatabaseModule;
import com.gurpster.facapemobile.di.module.ApplicationModule;
import com.gurpster.facapemobile.di.module.GradeRepositoryModule;
import com.gurpster.facapemobile.di.module.PreferenceModule;
import com.gurpster.facapemobile.di.module.ScheduleRepositoryModule;
import com.gurpster.facapemobile.di.module.ServiceBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by Williaan Lopes (d3x773r) on 05/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Singleton
@Component(modules =
        {
            ApiModule.class,
            AppDatabaseModule.class,
            GradeRepositoryModule.class,
            ScheduleRepositoryModule.class,
            PreferenceModule.class,
            ApplicationModule.class,
            ActivityBindingModule.class,
            ServiceBindingModule.class,
            AndroidSupportInjectionModule.class,
            AndroidInjectionModule.class
        })
public interface AppComponent extends AndroidInjector<StartApplication> {

    GradeRepository getGradeRepository();

    // Gives us syntactic sugar. we can then do DaggerAppComponent.builder().application(this).build().inject(this);
    // never having to instantiate any modules or say which module we are passing the application to.
    // StartApplication will just be provided into our app graph now.
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
