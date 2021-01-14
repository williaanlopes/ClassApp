package com.gurpster.facapemobile.di.module;

import com.gurpster.facapemobile.Main;
import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.view.activities.DrawerActivity;
import com.gurpster.facapemobile.view.activities.DrawerModule;
import com.gurpster.facapemobile.view.activities.LoginActivity;
import com.gurpster.facapemobile.view.activities.MainActivity;
import com.gurpster.facapemobile.view.activities.PlaceActivity;
import com.gurpster.facapemobile.view.activities.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Williaan Lopes (d3x773r) on 08/01/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public abstract class ActivityBindingModule {

//    @ActivityScoped
//    @ContributesAndroidInjector(modules = GradeModule.class)
//    abstract GradeFragment gradeActivity();

//    @ActivityScoped
//    @ContributesAndroidInjector
//    abstract InitDataService initDataService();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract Main appActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashScreenActivity splashScreenActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = DrawerModule.class)
    abstract DrawerActivity drawerActivity();

//    @ActivityScoped
//    @ContributesAndroidInjector(modules = DrawerModule.class)
//    abstract Main appActivity();

//    @ActivityScoped
//    @ContributesAndroidInjector(modules = StatisticsModule.class)
//    abstract StatisticsActivity statisticsActivity();

//    @ActivityScoped
//    @ContributesAndroidInjector(modules = TaskDetailPresenterModule.class)
//    abstract TaskDetailActivity taskDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity loginActivity();

//    @ActivityScoped
//    @ContributesAndroidInjector(modules = PlaceModule.class)
//    abstract PlaceActivity placeActivity();
}
