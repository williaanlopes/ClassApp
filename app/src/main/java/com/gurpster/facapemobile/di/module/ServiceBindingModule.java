package com.gurpster.facapemobile.di.module;

import com.gurpster.facapemobile.di.ActivityScoped;
import com.gurpster.facapemobile.service.GradeService;
import com.gurpster.facapemobile.service.InitDataService;
import com.gurpster.facapemobile.view.ScheduleWidgetService;
import com.gurpster.facapemobile.view.widget.ScheduleListWidgetService;
import com.gurpster.facapemobile.view.widget.ScheduleWidget;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Williaan Lopes (d3x773r) on 09/02/2018.
 * Gurpster.Inc
 * gurpster.inc@gmail.com
 */

@Module
public abstract class ServiceBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract InitDataService initDataService();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract GradeService gradeService();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ScheduleWidgetService scheduleWidgetService();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ScheduleListWidgetService scheduleListWidgetService();
}
